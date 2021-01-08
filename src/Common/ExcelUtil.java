package Common;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Wayne
 * @Classname ExcelUtil
 * @Description TODO
 * @Date 20/4/17 下午11:36
 * @Version V1.0
 */
public class ExcelUtil {

    public static void main(String[] args) throws Exception{
        String filePath = "/Users/wangyang/Downloads/11.xlsx";
        List<Map> list = new ExcelUtil().readWExcelWord(filePath, 0);
        ArrayList<String> result = new ArrayList<>();
        for (Map map : list) {
            String userId = MapUtils.getString(map, "cell1");
            String rpIdList = MapUtils.getString(map, "cell4");
            String[] rpIdArr = rpIdList.split(",");
            for (int index = 0; index < rpIdArr.length -1 ; index++) {
                String str = userId + "," + rpIdArr[index];
                result.add(str);
            }
        }
        String backFilePath = "/Users/wangyang/Downloads/电子券返销.txt";
        FileUtil.writeLines(backFilePath, result);
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MONTH, 1);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
//        System.out.println(simpleDateFormat.format(calendar.getTime()));
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//        String requestTime = simpleDateFormat.format(new Date());
//        System.out.println(requestTime);


    }


    public List<Map> readWExcelWord(String filePath, int sheetNum) throws Exception {
        Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath));
        Sheet sheet = workbook.getSheetAt(sheetNum);
        System.out.println(workbook.getNumberOfSheets());
        if (null == sheet) {
            System.out.println("Excel文件" + filePath + "中sheet页为空");
        }
        Map containMap = new HashMap();
        List<Map> list = new ArrayList<>();
        for (int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (null == row) {
                continue;
            }
            Map map = new HashMap();
            map.put("cell1", getCellValue(row, 0));
            map.put("cell2", getCellValue(row, 1));
            map.put("cell3", getCellValue(row, 2));
            map.put("cell4", getCellValue(row, 3));
            map.put("cell5", getCellValue(row, 4));
            map.put("cell6", getCellValue(row, 5));
            map.put("cell7", getCellValue(row, 6));
            System.out.println("第"+ rowIndex + "行数据为：" + JSON.toJSONString(map));
            list.add(map);
        }
        return list;
    }



    private String getCellValue(Row row, int i) {
        String value = "";
        Cell cell = row.getCell(i);
        if(cell != null){
            row.getCell(i).setCellType(HSSFCell.CELL_TYPE_STRING);
            value = row.getCell(i).getStringCellValue();
        }
        return value;
    }

    private Date getCellDate(Row row, int i) {
        String value = "";
        Cell cell = row.getCell(i);
        if(cell != null){
            row.getCell(i).setCellType(HSSFCell.CELL_TYPE_STRING);
            value = row.getCell(i).getStringCellValue();
        }
        Date setupTime = HSSFDateUtil.getJavaDate(Double.valueOf(value));
        return setupTime;
    }

}
