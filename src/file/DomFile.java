package file;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;

/**
 * @author Wayne
 * @Classname DomFile
 * @Description TODO
 * @Date 20/11/22 下午1:37
 * @Version V1.0
 */
public class DomFile {

    public static void main(String[] args) {
        String dirPath = "/Users/wangyang/Downloads/data/cnbc";
        File dir = new File(dirPath);
        String[] fileNameList = dir.list();
        Set<String> contentList = new HashSet<>();
        HashSet<Map> contents = new HashSet<>();
        ArrayList<String> result = new ArrayList<>();
        for (int index = 0; index < fileNameList.length; index++) {
            String filePath = dirPath + "/" + fileNameList[index];
//            contentList.add(getContent(filePath));
            getContent(filePath, contents);
        }

//        HashSet<String> contentList = new HashSet<>();
//        ArrayList<String> result = new ArrayList<>();
//        String filePath = "/Users/wangyang/Downloads/content-1113/result/cnn.txt";
//        readLines(filePath, contentList);
        for (Map info : contents) {
            String time = MapUtils.getString(info, "time");
            String line = MapUtils.getString(info, "content");
            if (StringUtils.isNotBlank(time) && time.contains("2020") && !(time.contains("Oct") || time.contains("Dec") || time.contains("Nov"))) {
                contentList.add(line);
            }
        }


        for (String content : contentList) {
            if (StringUtils.isBlank(content)) {
                continue;
            }
            if (content.indexOf("<img") > 0) {
                content = content.substring(0, content.indexOf("<img"));
            }
            System.out.println(content);
            String[] str = content.split("[,!?]");
//            String[] str = content.split("[.!?]");
            for (int index = 0; index < str.length; index++) {
                if (StringUtils.isBlank(str[index])) {
                    continue;
                }
                result.add(str[index].replaceAll("\n", ""));
            }
        }
        String resultPath = "/Users/wangyang/Downloads/data/cnbc-20201225-douhao.txt";
        writeLines(resultPath, result);
        System.out.println("文件大小为：" + contentList.size() + ", 分句后条数为：" + result.size());
//        String filePath = "/Users/wangyang/Downloads/content-1113/target/content-1113_77348922_1605823406821.xml";

    }

    public static void readLines(String file, HashSet<String> lines) {
        BufferedReader reader = null;

        try {

            reader = new BufferedReader(new FileReader(new File(file)));

            String line = null;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeLines(String file, ArrayList<?> counts) {
        BufferedWriter writer = null;

        try {

            writer = new BufferedWriter(new FileWriter(new File(file)));

            for (int i = 0; i < counts.size(); i++) {
                writer.write(counts.get(i) + "\r\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static String getContent(String filePath) {
        try {
            File file = new File(filePath);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            String content = document.getElementsByTagName("content").item(0).getFirstChild().getNodeValue();
//            System.out.println("内容为：" + content);
            return content;
        } catch (Exception e) {
            System.out.println(filePath + "处理异常");
            e.printStackTrace();
            return null;
        }

    }

    public static void getContent(String filePath, HashSet<Map> content) {
        try {
            File file = new File(filePath);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            NodeList nodeList = document.getElementsByTagName("item");
            for (int index = 0; index < nodeList.getLength(); index++) {
                NodeList chileNodeList = nodeList.item(index).getChildNodes();
                Map info = new HashMap();
                for (int i = 0; i < chileNodeList.getLength(); i++) {
                    if ("time".equalsIgnoreCase(chileNodeList.item(i).getNodeName())) {
                        info.put("time", chileNodeList.item(i).getFirstChild().getNodeValue());
                    }
                    if ("content".equalsIgnoreCase(chileNodeList.item(i).getNodeName())) {
                        info.put("content", chileNodeList.item(i).getFirstChild().getNodeValue());
                    }
                }
                if (MapUtils.isNotEmpty(info)) {
                    content.add(info);
                }
            }
//            System.out.println("内容为：" + content);
        } catch (Exception e) {
            System.out.println(filePath + "处理异常");
            e.printStackTrace();
        }

    }



}
