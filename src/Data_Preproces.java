import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Data_Preproces {
  public static void main(String[] args) {
    try {
      FileReader fr = new FileReader(
          "G:/小论文2/data/微博数据/data-微博/fedex.csv");
      BufferedReader br = new BufferedReader(fr);
      BufferedWriter bw = new BufferedWriter(
          new OutputStreamWriter(
              new FileOutputStream(
                  new File(
                      "G:/小论文2/data/微博数据/data-微博/abc.txt"))));
      int n = 0;
      String line = "";
      while (line != null) {
        line = br.readLine();
        // ------------预处理数据，形成<Doci>XX XXXX XX XXX<Doci>格式
        line = line.substring(line.indexOf("http://")); // 去掉http://前面乱七八糟的，微博号、中文昵称等之类的。
        // 用正则表达式提取中文，其他的一切都变成空格。多个空格压缩为一个，首尾空格压缩
        String reg = "[^\u4e00-\u9fa5]"; // 正则匹配非中文字符、英文、数字等
        Matcher mat = Pattern.compile(reg).matcher(line);
        while (mat.find()) {
          // System.out.print(mat.group()+"");
          line = line.replace(mat.group(), " ");// 找到所有非中文的字符英文数字等等，将其在line中替换为空格
        }
        // -----压缩词语之间的空格？-暂时不，保留词语之间的距离当做一种信息---
        // ----------------------
        /* -----助、代、介、象声等不能组成词的词也换成空格？需要分词~
         * 分词成" 把/pba"等,通过pba等找到那些词，替换原话中的地方为空格
         *
         */
        // ----------------------
        line = line.trim();
        // ----------------------------
        System.out.println(line);
        bw.write(line + "\n");    //读入时，加一个换行符号
        if (n > 6)
          break;
        n++;
      }
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

