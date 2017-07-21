package com.ffwb.service.impl;

import com.ffwb.DTO.QuestionDTO;
import com.ffwb.dao.*;
import com.ffwb.entity.*;
import com.ffwb.model.PageListModel;
import com.ffwb.service.QuestionService;
import com.ffwb.utils.JsonType;
import com.ffwb.utils.TagBuilt;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Autowired
    private AnalysisDao analysisDao;
    @Autowired
    private UserDao userDao;

    private  String judgeQuestionPath = "data/dictionary/judgeQuestion.txt";
//    /**
//     * 上传试卷 试题
//     * @param multipartFile
//     * @param managerId
//     * @return
//     */
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
                String pattern="\\d+";
                Pattern r=Pattern.compile(pattern);
                Matcher m=r.matcher(lineStr);
                if(m.matches()){
                    lineStr="";
                }
                else {
                    lineStr += "\n";
                }
                description += lineStr;
                while ((lineStr = br.readLine()) != null) {
                    if (lineStr.length() > 4 && ((lineStr.substring(0, 4).equals("正确答案"))))
                        break;
                    if(lineStr.length()>=4&&(lineStr.substring(0,4).equals("你的答案")))
                        break;
                    Matcher mm=r.matcher(lineStr);

                    if(mm.matches()){
                        lineStr="";
                    }else {
                        lineStr += "\n";
                    }
                    description += lineStr;
                }

                question.setDescription(description);
                //question.setLabel("java");
                question.setLabel("web前端");
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
                String tmpOption="";
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
                    }
                    tmpOption=option;
                    map.put(ch.toString(), option);
                }
                question.setOptionJson(JsonType.simpleMapToJsonStr(map));
                question.setAlive(1);
                question.setManager(manager);
                question.setType(1);
                question.setDifficulty(3.0);
                question.setScore(2);
                questionDao.save(question);

                while (!(lineStr = br.readLine()).equals("纠错")) {

                }

                String tmpSolution=null;
                if(!((lineStr=br.readLine()).substring(0,6).equals("可能的解答0"))){
                    String analysis="";
                    lineStr+="\n";
                    analysis+=lineStr;
                    while((lineStr=br.readLine())!=null){
                        if(lineStr.length()>5&&lineStr.substring(0,5).equals("可能的解答"))
                            break;
                        lineStr+="\n";
                        analysis+=lineStr;
                    }
                    if(!(analysis.substring(7).equals(""))) {
                        Analysis a = new Analysis();
                        Random rand=new Random();
                        long userId=rand.nextInt(8)+9;//产生1-8之间的随机数作为用户ID（+1）或者产生9-16的随机数
                        a.setUser(userDao.findOne(userId));
                        a.setAlive(1);
                        a.setContent(analysis.substring(7));
                        a.setCreatedTime(new Date());
                        a.setQuestion(question);
                        analysisDao.save(a);
                    }
                    tmpSolution+=analysis;
                    analysis=null;
                    if(!(lineStr.equals("解答结束"))&&(!(lineStr.substring(0,6).equals("可能的解答0")))){
                        lineStr+="\n";
                        analysis+=lineStr;
                        while((lineStr=br.readLine())!=null){
                            if(!(lineStr.equals("解答结束"))) {
                                lineStr+="\n";
                                analysis += lineStr;
                            }
                            else break;
                        }
                        if(!(analysis.substring(11).equals(""))) {
                            Analysis b = new Analysis();
                            Random rand=new Random();
                            long userId=rand.nextInt(8)+9;
                            b.setUser(userDao.findOne(userId));
                            b.setQuestion(question);
                            b.setCreatedTime(new Date());
                            b.setContent(analysis.substring(11));
                            b.setAlive(1);
                            analysisDao.save(b);
                        }
                    }
                    tmpSolution+=analysis;
                    //处理关于tag标签：包含题干、选项和解答
                    String desAndOp=description+tmpOption+tmpSolution;
                    Set<String> tmpTag= TagBuilt.isWhat(desAndOp);
                    if(null!=tmpTag) {
                        Set<Tag> theTags = new HashSet<>();
                        for (String s : tmpTag) {
                            Tag tag = tagDao.findByContentAndAlive(s, 1);
                            theTags.add(tag);
                        }
                        question.setTags(theTags);
                        questionDao.save(question);
                        questions.add(question);
                    }else{
                        question.setAlive(0);
                        questionDao.save(question);
                    }
                }
            }
        }catch(NullPointerException e){
            e.printStackTrace();
            System.out.print("dksfjd");
        }catch(Exception ee){
            ee.printStackTrace();
        }finally{
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
    public PageListModel getQuestionsByConditions(String label, int type, int pageIndex, int pageSize, String sortField, String sortOrder) {
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
    public List<Question> getQuestionByTag(int type, List<Tag> tags) {
        List<Question> questions = questionDao.findAll();
        List<Question> questionList = new ArrayList<>();
        for (Question question: questions){
            if (question.getAlive() ==1 && question.getTags() != null && question.getType() == type ){
                for (Tag tag : question.getTags()){
                    if (tags.contains(tag)){
                        questionList.add(question);
                    }
                }
                questionList.add(question);
            }
        }
        return questionList;
    }

    private Specification<Question> buildSpecifications(String label, int type){
        final String fLabel = label;
        final int fType = type;

        Specification<Question> specification = new Specification<Question>(){
            @Override
            public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if(type != 0){
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

    @Override
    public void convertSolution(){
        List<Question> questions = questionDao.findAll();
        for (Question question : questions){
            if(question.getSolution() == null || question.getSolution().equals("你的答案(错误)")){
                question.setAlive(0);
                questionDao.save(question);
                continue;
            }
            if (question.getSolution().contains(":")) {
                String solution = question.getSolution().split(":")[1];
                //String solution = "正确答案:ABCD";
                //List<String> solutionList = new ArrayList<String>();
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < solution.length(); i++) {
                    sb.append(solution.substring(i, i + 1)).append(" ");
                }
                question.setSolution(sb.substring(0, sb.length() - 1));
                questionDao.save(question);
            }
        }
    }

    @Override
    public void addJudgeQuestion() throws IOException {
        File input = new File(judgeQuestionPath);
        InputStream is = new FileInputStream(input);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        //StringBuffer sb = new StringBuffer();
        String temp;
        while((temp = br.readLine()) != null) {
            if(temp.equals("")){
                continue;
            }
            int index = temp.indexOf("、");
            String description = temp.substring(index+1, temp.length()-3);

            Question question = new Question();
            question.setDescription(description);
            question.setSolution(temp.substring(temp.length()-2, temp.length()-1));
            question.setManager(managerDao.findOne(1l));
            question.setAlive(1);
            question.setDifficulty(3);
            question.setScore(1);
            question.setType(2);
            //处理关于tag标签：包含题干、选项和解答
            Set<String> tmpTag= TagBuilt.isWhat(description);
            if(null!=tmpTag) {
                Set<Tag> theTags = new HashSet<>();
                for (String s : tmpTag) {
                    Tag tag = tagDao.findByContentAndAlive(s, 1);
                    theTags.add(tag);
                }
                question.setTags(theTags);
            }
            questionDao.save(question);
        }
    }

}
