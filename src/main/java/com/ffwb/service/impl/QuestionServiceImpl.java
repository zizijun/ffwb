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
import java.io.*;
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
//    @Override
//    public int upload(MultipartFile multipartFile, Long managerId) throws IOException {
//        Manager manager = managerDao.findOne(managerId);
//        if (manager == null){
//            return -1;
//        }
//        List<Question> questions = new ArrayList<Question>();
//        File file = File.createTempFile("./doc","temp");
//        multipartFile.transferTo(file);
//        ExcelReader excelReader = new ExcelReader(file);
//        XSSFSheet sheet = excelReader.getWb().getSheetAt(0);
//        //获得当前sheet的结束行
//        int lastRowNum = sheet.getLastRowNum();
//        for (int i = 1; i < lastRowNum; i++){
//            Question question = new Question();
//            question.setManager(manager);
//            XSSFRow row = sheet.getRow(i);
//            XSSFCell cell;
//            cell = row.getCell(0);
//            cell.setCellType(Cell.CELL_TYPE_STRING);
//            //题干
//            question.setDescription(cell.getStringCellValue());
//            //答案
//            cell = row.getCell(1);
//            cell.setCellType(Cell.CELL_TYPE_STRING);
//            question.setSolution(cell.getStringCellValue());
//            //标签
//            cell = row.getCell(2);
//            cell.setCellType(Cell.CELL_TYPE_STRING);
//            question.setType(cell.getStringCellValue());
//            //option
//            cell = row.getCell(3);
//            if (cell!= null && cell.getStringCellValue() != null){
//                Map<String, String> options = new HashMap<String, String>();
//                for (int j = 3; j < 7;j++){
//                    cell = row.getCell(j);
//                    cell.setCellType(Cell.CELL_TYPE_STRING);
//                    Character ch  = (char) (62+j);
//                    options.put(ch.toString(),cell.getStringCellValue());
//                }
//
//                question.setOptionJson(JsonType.simpleMapToJsonStr(options));
//            }
//            question.setAlive(1);
//            questions.add(question);
//            questionDao.save(question);
//        }
//        //questionDao.save(questions);
//        return questions.size();
//    }


    @Override
    public int upload(MultipartFile multipartFile, Long managerId) throws IOException {
        Manager manager=managerDao.findOne(managerId);
        if(manager==null)
            return -1;

        File file = File.createTempFile("./doc","temp");
        multipartFile.transferTo(file);
        FileInputStream fis=new FileInputStream(file);
        BufferedReader br=new BufferedReader(new InputStreamReader(fis,"UTF-8"));

        List<Question> questions=new ArrayList<Question>();
        String lineStr="";
        try {
            while ((lineStr = br.readLine()) != null) {
                Question question = new Question();
                //if(lineStr.substring(0,4).equals("题目描述")) {
                String description = "";
                description += lineStr;
                while ((lineStr = br.readLine()) != null) {
                    if (lineStr.length() > 4 && ((lineStr.substring(0, 4).equals("正确答案"))))
                        break;
                    description += lineStr;
                }

                question.setDescription(description);

                String[] tmp = lineStr.split(" ");
                String solution = "";
                for (String s : tmp) {
                    if (!(s.equals("你的答案:")))
                        solution += s;
                    else break;
                }
                question.setSolution(solution);//正确答案：A
                int count = 0;
                Map<String, String> map = new HashMap<String, String>();
                lineStr = br.readLine();
                while (lineStr.substring(0, 4).equals("选项描述")) {
                    if (lineStr.length() > 7 && lineStr.substring(6, 8).equals("添加"))
                        break;
                    Character ch = (char) (65 + count);
                    count++;
                    String option = "";
                    option += lineStr.substring(6);

                    while ((lineStr = br.readLine()) != null) {
                        if (lineStr.length() > 4 && !(lineStr.substring(0, 4).equals("选项描述")))
                            option += lineStr;
                        else if (lineStr.length() <= 4)
                            option += lineStr;
                        else {
                            break;
                        }
                        ;
                    }
                    map.put(ch.toString(), option);
                }
                question.setOptionJson(JsonType.simpleMapToJsonStr(map));
                question.setAlive(1);
                question.setManager(manager);
                question.setType("选择题");
                question.setDifficulty(3.0);
                question.setScore(2);
                questionDao.save(question);
                questions.add(question);
                while (!(lineStr = br.readLine()).equals("纠错")) {
                    lineStr = br.readLine();
                }
                if(!(lineStr.equals("可能的解答0"))){

                }
            }
        }catch(NullPointerException e){

        }finally {
            return questions.size();
        }
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

    @Override
    public List<Question> getQuestionByTag(String type, Tag tag) {
        List<Question> questions = questionDao.findAll();
        List<Question> questionList = new ArrayList<>();
        for (Question question: questions){
            if (question.getAlive() ==1 && question.getTags() != null && question.getTags().contains(tag) && question.getType() != null && question.getType().equals(type)){
                questionList.add(question);
            }
        }
        return questionList;
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

            dto.setTags(question.getTags());
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
        question.setTags(questionDTO.getTags());
        if(questionDTO.getOptionJson()!=null){
            question.setOptionJson(JsonType.simpleMapToJsonStr(questionDTO.getOptionJson()));
        }
        return question;
    }

}
