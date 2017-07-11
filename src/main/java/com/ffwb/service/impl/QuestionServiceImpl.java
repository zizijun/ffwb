package com.ffwb.service.impl;

import com.ffwb.DTO.QuestionDTO;
import com.ffwb.dao.ManagerDao;
import com.ffwb.dao.QuestionDao;
import com.ffwb.entity.Manager;
import com.ffwb.entity.Question;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jinchuyang on 2017/6/22.
 */
@Service("QuestionService")
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private ManagerDao managerDao;

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

    @Override
    public boolean labelQuestions(List<QuestionDTO> dto) {
        for(QuestionDTO q:dto){
            Question question=questionDao.findOne(q.getId());
            List<String> label=q.getLabel();
            String nlabel="";
            for(String l:label)
                nlabel+=l+" ";                //以空格分割label
            question.setLabel(nlabel);
            questionDao.save(question);
        }
        return true;
    }

    @Override
    public boolean updateQuestions(List<QuestionDTO> dto) {
        for(QuestionDTO q:dto){
            Question question=questionDao.findOne(q.getId());
            List<String> label=q.getLabel();
            String nlabel="";
            for(String l:label)
                nlabel+=l+" ";                //以空格分割label
            question.setLabel(nlabel);
            question.setDescription(q.getDescription());
            question.setType(q.getType());
            question.setSolution(q.getSolution());
            if(q.getOptionJson()!=null){
                question.setOptionJson(JsonType.simpleMapToJsonStr(q.getOptionJson()));
            }
            questionDao.save(question);
        }
        return true;
    }

    @Override
    public boolean addQuestions(List<QuestionDTO> dto,Long managerId) {
        Manager manager = managerDao.findOne(managerId);
        if (manager == null) return false;
        List<Question> questionList=dto2Pojo(dto);
        for(Question q:questionList){
            q.setManager(manager);
            questionDao.save(q);
        }
        return true;
    }

    @Override
    public boolean deleteQuestions(List<QuestionDTO> dto) {
        for(QuestionDTO q:dto){
            Question question=questionDao.findOne(q.getId());
            if(question!=null)
                question.setAlive(0);
        }
        return true;
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
            if(question.getOptionJson() != null){
                Map map = JsonType.getData(question.getOptionJson());
                dto.setOptionJson(map);
            }
            questionDTOList.add(dto);
        }
        return questionDTOList;
    }

    /**
     * pojo2dto
     * @param dto
     * @return
     */
    private List<Question> dto2Pojo(List<QuestionDTO> dto) {
        List<Question> questionList=new ArrayList<>();
        for (QuestionDTO q : dto) {
            Question question = new Question();
            question.setDescription(q.getDescription());
            question.setSolution(q.getSolution());
            question.setType(q.getType());
            question.setAlive(1);
            List<String> label = q.getLabel();
            String nlabel = "";
            for (String l : label)
                nlabel += l + " ";                //以空格分割label
            question.setLabel(nlabel);
            if (q.getOptionJson() != null) {
                question.setOptionJson(JsonType.simpleMapToJsonStr(q.getOptionJson()));
            }
            questionList.add(question);
        }
        return questionList;
    }


}
