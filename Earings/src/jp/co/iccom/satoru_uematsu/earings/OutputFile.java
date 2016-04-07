package jp.co.iccom.satoru_uematsu.earings;

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

	public void output(String path,Map<String,String> definitionData,Map<String,BigDecimal> earingsData){
		Map outputData = new HashMap();

		try {
			//パスの区切りは環境依存するので実行環境により変わるようにする
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path + File.separatorChar + trgFileName)));

			//定義ファイルのコードをiteratorに保持
			Iterator<String> codes = definitionData.keySet().iterator();
			while(codes.hasNext()){
				String code = codes.next();
				String name = definitionData.get(code);
				BigDecimal sumAmount = earingsData.get(code);
				//売上額がなかったものは「null」と表示されるので「0」を入れる
				if(sumAmount == null){
					sumAmount = new BigDecimal(0);
				}
				//コード+名前をキーとして設定
				outputData.put(code + "," + name, sumAmount);
			}
			//コード別集計ファイルのmapを合計金額で降順するためにソート
			List<Map.Entry> entries = new LinkedList(outputData.entrySet());
			Collections.sort(entries, new Comparator<Map.Entry>() {
				public int compare(Map.Entry o1,Map.Entry o2) {
					BigDecimal currentAmount = (BigDecimal) o1.getValue();
					BigDecimal nextAmount = (BigDecimal) o2.getValue();
					return nextAmount.compareTo(currentAmount);
				}
			});
			for (Map.Entry entry : entries) {
				//改行コードは環境依存するので実行環境により変わるようにする
				bw.write(entry.getKey() + "," + entry.getValue() +  System.getProperty("line.separator"));
			}
			bw.close();
		} catch (IOException e) {
			System.out.println("予期せぬエラーが発生しました");
			e.printStackTrace();
		}
	}
}
