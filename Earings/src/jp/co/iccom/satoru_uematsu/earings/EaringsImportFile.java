package jp.co.iccom.satoru_uematsu.earings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class EaringsImportFile {

	public HashMap fileImport(String path){
		HashMap earingsFileData = new HashMap();

		FileReader fr = null;
		BufferedReader br = null;

		try{
			//ディレクトリの読み込み
			File fileDir = new File(path);
			//ディレクトリ内をすべてFileオブジェクトとして格納
			File[] fileList = fileDir.listFiles();
			ArrayList<String> earingsFiles = new ArrayList();

			for(int i = 0; i < fileList.length; i++){
				File file = fileList[i];

				//読み込んだディレクトリ内にフォルダーがあるかもしれないので除外する
				if(file.isFile()){
					String fileName = file.getName();
					//ファイルの拡張子[.rcd]を探す
					if(fileName.lastIndexOf(".rcd") != -1){
						String fileNo = fileName.substring(0, fileName.lastIndexOf(".rcd"));
						//ファイル名が8桁且つ数字のみを対象とする
						if(fileNo.length() == 8 && fileNo.matches("[0-9]*$")){
							earingsFiles.add(fileName);
						}
					}
				}
			}
			//連番チェック
			for(int i = 0; i < earingsFiles.size(); i++){
				/*	連番チェックする際に
				 *  1ループ間で2つの要素を取り出すので
				 *  リストの最大値に達したらbreakする
				 */
				if(i ==  earingsFiles.size()-1){
					break;
				}

				String fileName1 = earingsFiles.get(i);
				String fileName2 = earingsFiles.get(i + 1);

				//売上ファイル名は拡張子に[.rcd]があるので拡張子を削除する
				int fileNo1 = Integer.parseInt( fileName1.substring(0, fileName1.lastIndexOf(".rcd")));
				int fileNo2 = Integer.parseInt( fileName2.substring(0, fileName2.lastIndexOf(".rcd")));

				//連番ならばファイル名の番号の差異は「1」になる
				if(fileNo2 - fileNo1 != 1){
					System.out.println("売上ファイルが連番になっていません。");
					return null;
				}
			}
			//１つの売上ファイルから読み取ったデータを格納
			for(int i = 0; i < earingsFiles.size(); i++){
				Vector v = new Vector();
				String fileName = (String) earingsFiles.get(i);
				fr = new FileReader(path + File.separatorChar + fileName);
				br = new BufferedReader(fr);

				String s;
				while((s =  br.readLine()) != null){
					//データを格納
					v.add(s);
				}
				fr.close();
				//売上ファイルは3つのデータの仕様なので3つ以外は除外する
				if(v.size() != 3){
					System.out.println(fileName + "のフォーマットが不正です。");
					return null;
				}
				earingsFileData.put(fileName,v);
			}
		}catch(IOException e){
			System.out.println("売上ファイルが存在しません。");
			return null;
		}finally{
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return earingsFileData;
	}

	public HashMap sumEarings(HashMap earingsFileData,HashMap branchMap,HashMap commodityMap){
		HashMap hm = new HashMap();
		//売上ファイル名をsetとして取得
		Set fileNames = earingsFileData.keySet();
		Iterator ite = fileNames.iterator();

		while(ite.hasNext()){
			String fileName = (String) ite.next();

			//earingsFileData内は売上ファイル名をキーとして売上データをvectorで保持している
			Vector v = (Vector) earingsFileData.get(fileName);

			//要素１の支店コードを取得
			String branchCode = (String) v.get(0);
			//要素２の商品コードを取得
			String commondityCode = (String)v.get(1);
			//要素３の売上額を取得
			String amtStr = (String)v.get(2);

			if(! amtStr.matches("[0-9]*$") && amtStr.length() <= 10){
				System.out.println(fileName + "の売上額が不正です");
				return null;
			}
			BigDecimal amt =  new BigDecimal(amtStr);

			//売上ファイル内の支店コードが支店定義ファイルに存在するかチェック
			if(! branchMap.containsKey(branchCode)){
				System.out.println(fileName + "の支店コードが不正です");
				return null;
			}
			//売上ファイル内の商品コードが支店定義ファイルに存在するかチェック
			if(! commodityMap.containsKey(commondityCode)){
				System.out.println(fileName + "の商品コードが不正です");
				return null;
			}

			//各集計ファイルごとにmapに分ける
			hm = setMap(branchCode,hm,amt);
			if(hm == null){
				return null;
			}
			hm = setMap(commondityCode,hm,amt);
			if(hm == null){
				return null;
			}
		}
		return hm;
	}

	protected HashMap setMap(String key,HashMap hm,BigDecimal amt){
		//合計金額を格納
		BigDecimal sumAmt = new BigDecimal(0);

		//各コード別に集計
		if(hm.containsKey(key)){
			sumAmt = (BigDecimal) hm.get(key);
			sumAmt = sumAmt.add(amt);
			//桁数チェック
			if(sumAmt.precision() > 10){
				System.out.println("合計金額が10桁を超えました");
				return null;
			}
			hm.put(key, sumAmt);

		}else{
			hm.put(key, amt);
		}
		return hm;
	}
}
