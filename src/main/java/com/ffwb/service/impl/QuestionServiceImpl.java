package com.ffwb.service.impl;

import com.ffwb.DTO.QuestionDTO;
import com.ffwb.dao.ManagerDao;
import com.ffwb.dao.QuestionDao;
import com.ffwb.dao.TagDao;
import com.ffwb.entity.Manager;
import com.ffwb.entity.Question;
import com.ffwb.entity.Tag;
import com.ffwb.model.PageListModel;
import com.ffwb.service.QuestionService;
import com.ffwb.utils.ExcelReader;
import com.ffwb.utils.JsonType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by jinchuyang on 2017/6/22.
 */
@Service("QuestionService")
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private ManagerDao managerDao;
    @Autowired
    private TagDao tagDao;

    /**
     * 上传试卷 试题
     * @param multipartFile
     * @param managerId
     * @return
     */
    @Override
    public int upload(MultipartFile multipartFile, Long managerId) throws IOException {
        Manager manager = managerDao.findOne(managerId);
        if (manager == null){
            return -1;
        }
        List<Question> questions = new ArrayList<Question>();
        File file = File.createTempFile("./doc","temp");
        multipartFile.transferTo(file);
        ExcelReader excelReader = new ExcelReader(file);
        XSSFSheet sheet = excelReader.getWb().getSheetAt(0);
        //获得当前sheet的结束行
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 1; i < lastRowNum; i++){
            Question question = new Question();
            question.setManager(manager);
            XSSFRow row = sheet.getRow(i);
            XSSFCell cell;
            cell = row.getCell(0);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            //题干
            question.setDescription(cell.getStringCellValue());
            //答案
            cell = row.getCell(1);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            question.setSolution(cell.getStringCellValue());
            //标签
            cell = row.getCell(2);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            question.setType(cell.getStringCellValue());
            //option
            cell = row.getCell(3);
            if (cell!= null && cell.getStringCellValue() != null){
                Map<String, String> options = new HashMap<String, String>();
                for (int j = 3; j < 7;j++){
                    cell = row.getCell(j);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    Character ch  = (char) (62+j);
                    options.put(ch.toString(),cell.getStringCellValue());
                }

                question.setOptionJson(JsonType.simpleMapToJsonStr(options));
            }
            question.setAlive(1);
            questions.add(question);
            questionDao.save(question);
        }
        //questionDao.save(questions);
        return questions.size();
    }

    /**
     * 获取所有alive为1的问题
     * @return
     * @param pageIndex
     * @param pageSize
     * @param sortField
     * @param sortOrder
     */
    @Override
    public PageListModel getAllQuestions(int pageIndex, int pageSize, String sortField, String sortOrder) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder.toUpperCase().equals("DESC")) {
            direction = Sort.Direction.DESC;
        }
        Sort sort = new Sort(direction, sortField);
        Page<Question> questions = questionDao.findByAlive(1, new PageRequest(pageIndex, pageSize, sort));
        //pojo2dto
        List<QuestionDTO> questionDTOList = pojo2Dto(questions);
        PageListModel pageListModel = PageListModel.Builder().pageIndex(pageIndex).pageSize(pageSize).
                totalCount(questions.getTotalElements()).totalPage(questions.getTotalPages()).list(questionDTOList).build();
        return pageListModel;
    }

    /**
     * 根据条件搜索问题
     * @param label
     * @param type
     * @param pageIndex
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @return
     */
    @Override
    public PageListModel getQuestionsByConditions(String label, String type, int pageIndex, int pageSize, String sortField, String sortOrder) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder.toUpperCase().equals("DESC")) {
            direction = Sort.Direction.DESC;
        }
        Sort sort = new Sort(direction, sortField);

        Specification<Question> specification = this.buildSpecifications(label,type);
        Page<Question> questions = questionDao.findAll(Specifications.where(specification), new PageRequest(pageIndex, pageSize, sort));

        List<QuestionDTO> questionDTOList = pojo2Dto(questions);
        PageListModel pageListModel = PageListModel.Builder().pageIndex(pageIndex).pageSize(pageSize).
                totalCount(questions.getTotalElements()).totalPage(questions.getTotalPages()).list(questionDTOList).build();
        return pageListModel;
    }

//    @Override
//    public boolean labelQuestions(List<QuestionDTO> dto) {
//        for(QuestionDTO q:dto){
//            Question question=questionDao.findOne(q.getId());
//            List<String> label=q.getLabel();
//            String nlabel="";
//            for(String l:label)
//                nlabel+=l+" ";                //以空格分割label
//            question.setLabel(nlabel);
//            questionDao.save(question);
//        }
//        return true;
//    }

    @Override
    public int updateQuestions(List<QuestionDTO> dto) {
        int successTimes = 0;
        for(QuestionDTO q:dto){
            Question question=dto2pojo(q);
            if (question == null){
                continue;
            }
            question = questionDao.save(question);
            if (question != null){
                successTimes++;
            }
        }
        return successTimes;
    }



    @Override
    public int addQuestions(List<QuestionDTO> dtos,Long managerId) {
        Manager manager = managerDao.findOne(managerId);
        if (manager == null) return 0;
        //List<Question> questionList=dto2Pojo(dto);
        int successTimes = 0;
        for (QuestionDTO dto : dtos){
            Question question = dto2pojo(dto);
            if (question == null)
                continue;
            question.setManager(manager);
            question = questionDao.save(question);
            if (question != null){
                successTimes++;
            }
        }
        return  successTimes;
    }

    /**
     * 删除题目
     * @param dto
     * @return
     */
    @Override
    public int deleteQuestions(List<QuestionDTO> dto) {
        int successTimes = 0;
        for(QuestionDTO q:dto){
            Question question=questionDao.findOne(q.getId());
            if(question==null)
                continue;
            question.setAlive(0);
            question = questionDao.save(question);
            if (question != null){
                successTimes++;
            }
        }
        return successTimes;
    }

    private Specification<Question> buildSpecifications(String label, String type){
        final String fLabel = label;
        final String fType = type;

        Specification<Question> specification = new Specification<Question>(){
            @Override
            public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if(null!=fType && !"".equals(fType)){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("type"),fType));
                }
                if(null!=fLabel && !"".equals(fLabel)){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("label"),fLabel));
                }
                return criteriaBuilder.and(predicate);
            }

        };
        return specification;
    }

    /**
     * pojo2dto
     * @param questions
     * @return
     */
    private List<QuestionDTO> pojo2Dto(Page<Question> questions) {
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        for (Question question :questions){
            QuestionDTO dto = new QuestionDTO();
            dto.setId(question.getId());
            dto.setDescription(question.getDescription());
            dto.setSolution(question.getSolution());
            dto.setType(question.getType());
            dto.setLabel(question.getLabel());
            if(question.getOptionJson() != null){
                Map map = JsonType.getData(question.getOptionJson());
                dto.setOptionJson(map);
            }
            Set<Integer> tagIds = new HashSet<Integer>();
            for (Tag tag : question.getTags()){
                tagIds.add(tag.getId().intValue());
            }
            dto.setTagIds(tagIds);
            questionDTOList.add(dto);
        }
        return questionDTOList;
    }

    /**
     * DTO2pojo
     * @return
     */
    private Question dto2pojo(QuestionDTO questionDTO){
        Question question=questionDao.findOne(questionDTO.getId());
        if (question == null){
            return question;
        }
        question.setLabel(questionDTO.getLabel());
        question.setDescription(questionDTO.getDescription());
        question.setType(questionDTO.getType());
        question.setSolution(questionDTO.getSolution());
        Set<Tag> tags = new HashSet<>();
        for (Integer id :questionDTO.getTagIds()){
            Tag tag = tagDao.findByIdAndAlive(id, 1);
            if (tag != null){
                tags.add(tag);
            }
        }
        question.setTags(tags);
        if(questionDTO.getOptionJson()!=null){
            question.setOptionJson(JsonType.simpleMapToJsonStr(questionDTO.getOptionJson()));
        }
        return question;
    }

}
