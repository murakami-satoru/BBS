package iccom.satoru_uematsu.earings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

abstract public class OutputFile {

	String trgFileName;

	public void outPut(String pass,HashMap defMap,HashMap earingsMap){

		HashMap hm = new HashMap();

		try {
			File file = new File(pass + "\\" + trgFileName);
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);

			//定義ファイルのコードをiteratorに保持
			Iterator ite = defMap.keySet().iterator();
			while(ite.hasNext()){
				String code = (String) ite.next();
				String name = (String) defMap.get(code);
				BigDecimal sumAmt = (BigDecimal) earingsMap.get(code);
				//売上額がなかったものは「null」と表示されるので「0」を入れる
				if(sumAmt == null){
					sumAmt = new BigDecimal(0);
				}
				//コード+名前をキーとして設定
				hm.put(code + "," + name, sumAmt);
			}

			//コード別集計ファイルのmapを合計金額で降順するためにソート
			List<Map.Entry> entries = new LinkedList(hm.entrySet());
			Collections.sort(entries, new Comparator<Map.Entry>() {
				public int compare(Map.Entry o1,Map.Entry o2) {
					BigDecimal int1 = (BigDecimal) o1.getValue();
					BigDecimal int2 = (BigDecimal) o2.getValue();
					return int2.compareTo(int1);
				}
			});

			for (Map.Entry entry : entries) {
				bw.write(entry.getKey() + ", " + entry.getValue() +  "\r\n");
			}

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
