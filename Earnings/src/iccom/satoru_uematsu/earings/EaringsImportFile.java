package iccom.satoru_uematsu.earings;

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

	public HashMap fileImport(String pass) throws MyException{
		HashMap earingsFileData = new HashMap();

		try{

			File file = new File(pass);
			String[] fileNames = file.list();
			ArrayList earingsFiles = new ArrayList();

			for(int i = 0; i < fileNames.length; i++){
				String fileName = fileNames[i];

				//ファイルの拡張子[.rcd]を探す
				if(fileName.lastIndexOf(".rcd") != -1){
					String fileNo = fileName.substring(0, fileName.lastIndexOf(".rcd"));
					//ファイル名8桁を探す
					if(fileNo.length() == 8){
						earingsFiles.add(fileName);
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

				String fileName1 = (String) earingsFiles.get(i);
				String fileName2 = (String) earingsFiles.get(i + 1);

				//売上ファイル名は拡張子に[.rcd]があるので拡張子を削除する
				int fileNo1 = Integer.parseInt( fileName1.substring(0, fileName1.lastIndexOf(".rcd")));
				int fileNo2 = Integer.parseInt( fileName2.substring(0, fileName2.lastIndexOf(".rcd")));

				//連番ならばファイル名の番号の差異は「1」になる
				if(fileNo2 - fileNo1 != 1){
					throw new MyException("売上ファイルが連番になっていません。");
				}
			}
			//１つの売上ファイルから読み取ったデータを格納
			BufferedReader br = null;
			for(int i = 0; i < earingsFiles.size(); i++){
				Vector v = new Vector();

				String fileName = (String) earingsFiles.get(i);
				FileReader fr = new FileReader(pass + "\\" + fileName);

				br = new BufferedReader(fr);

				String s;
				int contentsCount = 1;
				while((s =  br.readLine()) != null){
					//売上ファイルに4データ以上入っていたらエラー出力
					if(contentsCount == 4){
						fr.close();
						throw new MyException(fileName + "のフォーマットが不正です。");
					}
					//データを格納
					v.add(s);
					//データ数のカウント
					contentsCount++;
				}
				fr.close();
				earingsFileData.put(fileName,v);
			}

			br.close();

		}catch(IOException e1){
			throw new MyException("売上ファイルが存在しません。");
		}
		return earingsFileData;
	}

	public HashMap sumEarings(HashMap earingsFileData,HashMap branchMap,HashMap commodityMap) throws MyException{
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
			BigDecimal amt =  new BigDecimal((String)v.get(2));

			//売上ファイル内の支店コードが支店定義ファイルに存在するかチェック
			if(branchMap.containsKey(branchCode) == false){
				throw new MyException(fileName + "の支店コードが不正です");
			}
			//売上ファイル内の商品コードが支店定義ファイルに存在するかチェック
			if(commodityMap.containsKey(commondityCode) == false){
				throw new MyException(fileName + "の商品コードが不正です");
			}

			//各集計ファイルごとにmapに分ける
			hm = setMap(branchCode,hm,amt);
			hm = setMap(commondityCode,hm,amt);
		}
		return hm;
	}

	protected void ckSumAmt(BigDecimal sumAmt) throws MyException{

//		System.out.println(sumAmt + " : " + sumAmt.precision());

		if(sumAmt.precision() > 10){
			throw new MyException("合計金額が10桁を超えました");
		}
	}

	protected HashMap setMap(String key,HashMap hm,BigDecimal amt) throws MyException{
		//合計金額を格納
		BigDecimal sumAmt = new BigDecimal(0);

		//各コード別に集計
		if(hm.containsKey(key)){
			sumAmt = (BigDecimal) hm.get(key);
			sumAmt = sumAmt.add(amt);
			ckSumAmt(sumAmt);
			hm.put(key, sumAmt);

		}else{
			hm.put(key, amt);
		}
		return hm;
	}
}
