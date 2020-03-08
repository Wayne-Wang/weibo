import TwitterLDA.tweet;
import com.alibaba.fastjson.JSON;
import com.huaban.analysis.jieba.JiebaSegmenter;
import edu.berkeley.nlp.util.Lists;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wayne
 * @Classname FenCiService
 * @Description TODO
 * @Date 20/1/10 上午12:37
 * @Version V1.0
 */
public class FenCiService {

    public static void main(String[] args) throws Exception {
        FenCiService fenCiService = new FenCiService();
        // 微博拉取的文件路径，将需要计算情感值的内容放到博文一列中
        String fileName = "/Users/wangyang/Downloads//分词/fedex-xiugai.xlsx";
        /**
         * 读取excel中的内容
         */
        List<Map> list = fenCiService.readExcel(fileName);
        fenCiService.splitText(list);
        JiebaSegmenter jiebaSegmenter = new JiebaSegmenter();
        for (Map map : list) {
//            String text = MapUtils.getString(map, "text");
            List<String> splitText = (List<String>)MapUtils.getObject(map, "splitText");
            List<Map> splitContent = new ArrayList<>();
            for (String text : splitText) {
                Map fenJuMap = new HashMap();
                List<String> fenci = jiebaSegmenter.sentenceProcess(text);
                fenJuMap.put("splitText", text);
                fenJuMap.put("splitFenci", fenci);
                splitContent.add(fenJuMap);
            }
            map.put("splitContent", splitContent);
            System.out.println("分词后的数据为：" + JSON.toJSONString(map));
        }
        String stopFilePath = "/Users/wangyang/Downloads//微博数据/停用词.txt";
        List<String> stopWordList = fenCiService.readStopWord(stopFilePath);
        // 第一个参数是文件所在位置，第二个参数是第几个sheet页，从0开始代表第一个sheet页
        /**
         * 获取情感值字段信息，情感词和情感值，分别是第2个sheet页和第3个sheet页
         */
        Map<String, String> emotionWordList = fenCiService.readWExcelWord("/Users/wangyang/Downloads//微博数据/情感词典.xlsx", 0);
        Map<String, String> negativeWordList = fenCiService.readWExcelWord("/Users/wangyang/Downloads//微博数据/否定词典.xlsx", 0);
        /**
         * 获取程度副词字段信息，情感词和情感值，分别是第6个sheet页和第7个sheet页
         */
        Map<String, String> degreeWordList = fenCiService.readWExcelWord("/Users/wangyang/Downloads//微博数据/程度副词词典.xlsx", 0);
        Map<String, String> conjunctionWordList = fenCiService.readWExcelWord("/Users/wangyang/Downloads//微博数据/连词词典.xlsx", 0);
        System.out.println("情感词：" + emotionWordList.size() + ", 否定词：" + negativeWordList.size() + ", 程度副词；" + degreeWordList.size() + ", 连词： " + conjunctionWordList.size());
		/**
         * 去掉停用词
         */
        fenCiService.dealStopWord(list, stopWordList);
        /**
         * 处理情感词
         */
        fenCiService.dealEmotionWord(list, emotionWordList);
        fenCiService.dealPrefixWordList(list, negativeWordList, "negativeValue", "negativePrefix");
        /**
         * 处理程度副词
         */
        fenCiService.dealPrefixWordList(list, degreeWordList, "degreeValue", "degreePrefix");
        fenCiService.dealOtherWordList(list, conjunctionWordList, "conjunctionValue", "conjunctionPrefix");
        /**
         * 计算情感值
         * 每一句的情感值：同类型情感词相加，异类型情感词相乘
         * （1+1）--情感词库的值；2+1.5----副词的值 （-1）是否定词，因此（1+1）*（2+1.5）*（-1）=-7；实际上：1*2+（-1）+1.5*1=2.5。
         */
        fenCiService.dealFinalAnalysis(list);
        // 最后文件解析存储的文件地址
        /**
         * 生成最后的结果
         * 输入结果路径为：/Users/wangyang/Downloads/情感值分析结果.xls 此为mac盘符，windows盘符如：D:/Research/Projects/NTUproject/TLDA/data/
         */
        fenCiService.writeExcel("/Users/wangyang/Downloads//微博数据/情感分析结果-fedex.xls", list);
        System.out.println("任务执行结束");
    }

    private void splitText(List<Map> list) {
        for (Map map : list) {
            String text = MapUtils.getString(map, "text");
            if (StringUtils.isBlank(text)) {
                continue;
            }
			String subText = dealStr(text, 0);
            // 将博文分为子句的分隔符：中英文下的四种符号
            String[] splitStrArr = subText.split("[，。！？,.!?：: ]");
            System.out.println("数据[" + text + "]分割后数据为：" + JSON.toJSONString(splitStrArr));
            List<String> splitStrList = Lists.newList(splitStrArr);
            List<String> otherList = new ArrayList<>();
            for (String splitStr : splitStrList) {
                if (tweet.isNoisy(splitStr)) {
                    System.out.println(splitStr + "含有特殊字符去除");
                } else {
                    otherList.add(splitStr);
                }
            }
            map.put("splitText", otherList);
        }
    }
	private static String dealStr(String str, int beginIndex) {
        if (str.indexOf("@", beginIndex) > 0) {
            int index = str.indexOf("@", beginIndex);
            String subStr = str.substring(0, index) + " " + str.substring(index);
            beginIndex = index + 2;
            return dealStr(subStr, beginIndex);
        }
        return str;
    }

    private void writeExcel(String filePath, List<Map> list) throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();//定义工作薄
        HSSFSheet sheet = wb.createSheet("Sheet1");//创建一个sheet页
        HSSFRow row = sheet.createRow(0);//创建第一行
        row.createCell(0).setCellValue("微博博文");
//        row.createCell(1).setCellValue("分词结果");
        row.createCell(2).setCellValue("最终情感值");
        int count = 1;
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setWrapText(true);
        for (Map result : list) {
            System.out.println(JSON.toJSONString(result));
            HSSFRow row1 = sheet.createRow(count);
            row1.setRowStyle(cellStyle);
            row1.createCell(0).setCellValue(MapUtils.getString(result, "text"));
            String text = JSON.toJSONString(result);
            if (text.length() > 32700) {
                text = text.substring(0, 32700);
            }
//            row1.createCell(1).setCellValue(text);
            row1.createCell(2).setCellValue(MapUtils.getDoubleValue(result, "emotion_result", 0.00d));
            count++;
        }
        OutputStream out = new FileOutputStream(new File(filePath));
        wb.write(out);
        out.close();
    }

    private void dealFinalAnalysis(List<Map> list) {
        for (Map map : list) {
            List<Map> splitContentList = (List<Map>) MapUtils.getObject(map, "splitContent");
            double result = 0.00;
            for (Map splitContent : splitContentList) {
                double splitResult = 0.00;
                double emotionValue = MapUtils.getDoubleValue(splitContent, "emotionValue", 0.00);
                double negativeValue = MapUtils.getDoubleValue(splitContent, "negativeValue", 0.00);
                double degreeValue = MapUtils.getDoubleValue(splitContent, "degreeValue", 0.00);
                double conjunctionValue = MapUtils.getDoubleValue(splitContent, "conjunctionValue", 0.00);
                splitResult += emotionValue;
                if (0.00 != negativeValue) {
                    splitResult = splitResult * negativeValue;
                }
                if (0.00 != degreeValue) {
                    splitResult = splitResult * degreeValue;
                }
                if (0.00 != conjunctionValue) {
                    splitResult = splitResult * conjunctionValue;
                }
                result += splitResult;
            }
            map.put("emotion_result", result);
            System.out.println("解析情感值为：" + JSON.toJSONString(map));
        }
    }

    private void dealEmotionWord(List<Map> list, Map<String, String> wordMap) {
        for (Map map : list) {
            List<Map> splitContentList = (List<Map>) MapUtils.getObject(map, "splitContent");
            for (Map splitContent : splitContentList) {
                double value = 0.00;
                List<String> fenciList = (List<String>) MapUtils.getObject(splitContent, "removeWordFenci");
                List<String> matchWordList = new ArrayList<>();
                List<String> prefixWordList = new ArrayList<>();
                for (int index = 0; index < fenciList.size(); index++) {
                    String word = fenciList.get(index);
                    if (wordMap.containsKey(word)) {
                        double valueWord = MapUtils.getDoubleValue(wordMap, word, 0.00d);
                        matchWordList.add(word);
                        if (index != 0) {
                            String prefixWord = fenciList.get(index - 1);
                            prefixWordList.add(prefixWord);
                        }
                        System.out.println("情感值是：" + valueWord);
                        value = valueWord + value;
                    }
                }
                splitContent.put("emotionValue", value);
                splitContent.put("emotionPrefix", matchWordList);
                splitContent.put("prefixWordFenci", prefixWordList);
            }
            System.out.println("解析情感值为：" + JSON.toJSONString(map));
        }
    }

    private void dealPrefixWordList(List<Map> list, Map<String, String> wordMap, String key, String matchKey) {
        for (Map map : list) {
            List<Map> splitContentList = (List<Map>) MapUtils.getObject(map, "splitContent");
            for (Map splitContent : splitContentList) {
                double value = 0.00;
                List<String> fenciList = (List<String>) MapUtils.getObject(splitContent, "prefixWordFenci");
                List<String> matchWordList = new ArrayList<>();
                for (String word : fenciList) {
                    for (String keyWord : wordMap.keySet()) {
                        if (word.indexOf(keyWord) > 0) {
                            double valueWord = MapUtils.getDoubleValue(wordMap, keyWord, 0.00d);
                            matchWordList.add(word + "-" + keyWord);
                            System.out.println("情感值是：" + valueWord);
                            value = valueWord + value;
                        }
                    }
                }
                splitContent.put(key, value);
                splitContent.put(matchKey, matchWordList);
            }
            System.out.println("解析情感值为：" + JSON.toJSONString(map));
        }
    }

    private void dealOtherWordList(List<Map> list, Map<String, String> wordMap, String key, String matchKey) {
        for (Map map : list) {
            List<Map> splitContentList = (List<Map>) MapUtils.getObject(map, "splitContent");
            for (Map splitContent : splitContentList) {
                double value = 0.00;
                List<String> fenciList = (List<String>) MapUtils.getObject(splitContent, "removeWordFenci");
                List<String> matchWordList = new ArrayList<>();
                for (String word : fenciList) {
                    for (String keyWord : wordMap.keySet()) {
                        if (word.indexOf(keyWord) > 0) {
                            double valueWord = MapUtils.getDoubleValue(wordMap, keyWord, 0.00d);
                            matchWordList.add(word + "-" + keyWord);
                            System.out.println("情感值是：" + valueWord);
                            value = valueWord + value;
                        }
                    }
                }
                splitContent.put(key, value);
                splitContent.put(matchKey, matchWordList);
            }
            System.out.println("解析情感值为：" + JSON.toJSONString(map));
        }
    }

    private void dealStopWord(List<Map> list, List<String> stopWordList) {
        for (Map map : list) {
//            List<String> fenciList = (List<String>) MapUtils.getObject(map, "fenci");
            List<Map> splitContentList = (List<Map>) MapUtils.getObject(map, "splitContent");
            for (Map splitContent : splitContentList) {
                List<String> fenciList = (List<String>) MapUtils.getObject(splitContent, "splitFenci");
                List<String> removeWordFenciList = new ArrayList<>();
                for (String word : fenciList) {
                    if (!stopWordList.contains(word)) {
                        removeWordFenciList.add(word);
                    } else {
                        System.out.println("去除停用词:" + word);
                    }
                }
                splitContent.put("removeWordFenci", removeWordFenciList);
            }
            System.out.println("去停用词后的数据为：" + JSON.toJSONString(map));
        }

    }

    public Map readWExcelWord(String filePath, int sheetNum) throws Exception {
        Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath));
        Sheet sheet = workbook.getSheetAt(sheetNum);
        if (null == sheet) {
            System.out.println("Excel文件" + filePath + "中sheet页为空");
        }
        Map map = new HashMap();
        Map containMap = new HashMap();
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (null == row) {
                continue;
            }
            if (null == row.getCell(0) || null == row.getCell(1)) {
                System.out.println("第" + rowIndex + "行数据为空");
            }
            String key = getValue(row.getCell(0));
            String value = getValue(row.getCell(1));
            System.out.println("key=" + key + ", value=" + value);
            if (map.containsKey(key)) {
                containMap.put(key, value);
            }
            map.put(key, value);
        }
        System.out.println("总数为：" + sheet.getLastRowNum() + ", 实际获取数：" + map.keySet().size() + ",重复数据量：" + containMap.size());
        System.out.println("重复数据为:" + JSON.toJSONString(containMap));
        return map;
    }


    public List<String> readStopWord(String stopFilePath) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(stopFilePath), "GBK"));
        String line = null;
        int count = 1;
        List<String> stopWordList = new ArrayList<>();
        while (StringUtils.isNotBlank((line = reader.readLine()))) {
            System.out.println("第" + count + "行数据为：" + line);
            stopWordList.add(line);
            count++;
        }
        return stopWordList;
    }

    /**
     * 文件每列分别为关键字、博主照片、博主id，博主、博主主页、正文、发布地址、发布时间
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public List<Map> readExcel(String filePath) throws Exception {
        Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath));
        Sheet sheet = workbook.getSheetAt(0);
        if (null == sheet) {
            System.out.println("Excel文件" + filePath + "中sheet页为空");
        }
        int count = 1;
        List<Map> list = new ArrayList<>();
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (null == row) {
                continue;
            }
            Map map = new HashMap();
            map.put("key", getValue(row.getCell(0)));
            map.put("photo", getValue(row.getCell(1)));
            map.put("weibo_id", getValue(row.getCell(2)));
            map.put("weibo", getValue(row.getCell(3)));
            map.put("home_page", getValue(row.getCell(4)));
            map.put("text", getValue(row.getCell(5)));
            map.put("address", getValue(row.getCell(6)));
            map.put("publish_time", getValue(row.getCell(7)));
            System.out.println("数据明细：" + JSON.toJSONString(map));
            list.add(map);
            count++;
        }
        System.out.println("数据总量：" + count);
        return list;
    }

    private static String getValue(Cell cell) {
        if (null == cell) {
            return "";
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue()));
                }
                return String.valueOf(cell.getNumericCellValue());
            case Cell.CELL_TYPE_FORMULA:
                return String.valueOf(cell.getCellFormula());
            default:
                return String.valueOf(cell.getStringCellValue());
        }
    }


//    public static void main(String[] args) {
//        JiebaSegmenter jiebaSegmenter = new JiebaSegmenter();
//        String sentence = "鲁迅先生的那一句话：愿中国青年都摆脱冷气，只向上走，不必听自暴自弃者流的话。能做事的做事，能发声的发声，有一分热，发一分光，就像萤火一般，也可以在黑暗里发一点光，不必等候炬火。总之，华为加油，冲鸭！";
//        List<String>  result = jiebaSegmenter.sentenceProcess(sentence);
//        System.out.println(JSON.toJSONString(result));
//    }
}
