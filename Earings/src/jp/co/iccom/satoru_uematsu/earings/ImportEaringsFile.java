package jp.co.iccom.satoru_uematsu.earings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ImportEaringsFile {

	private Map branchLineData_;
	private Map commodityLineData_;

	public void setBranchLineData(Map branchLineData){
		branchLineData_ = branchLineData;
	}
	public void setCommodityLineData(Map commodityLineData){
		commodityLineData_ = commodityLineData;
	}
	public Map getBranchLineData(){
		return branchLineData_;
	}
	public Map getCommodityLineData(){
		return commodityLineData_;
	}

	public Map fileImport(String path){
		Map earingsFileList = new HashMap();

		//ディレクトリの読み込み
		File fileDir = new File(path);
		//ディレクトリ内をすべてFileオブジェクトとして格納
		File[] fileList = fileDir.listFiles();
		List<String> earingsFileNames = new ArrayList();
		for(File file : fileList){
			//読み込んだディレクトリ内にフォルダーがあるかもしれないので除外する
			if(! file.isDirectory()){
				String fileName = file.getName();
				//ファイルの拡張子[.rcd]を探す
				if(fileName.lastIndexOf(".rcd") != -1){
					String fileNo = fileName.replaceAll(".rcd", "");
					//ファイル名が8桁且つ数字のみを対象とする
					if(fileNo.length() == 8 && fileNo.matches("[0-9]*$")){
						earingsFileNames.add(fileName);
					}
				}
			}
		}
		//連番チェック
		for(int i = 0; i < earingsFileNames.size()-1; i++){

			String currentFileName = earingsFileNames.get(i);
			String nextFileName = earingsFileNames.get(i + 1);

			//ファイル名だけ抽出したいので「.rcd」はブランクで削除
			int currentFileNo = Integer.parseInt(currentFileName.replaceAll(".rcd", ""));
			int nextFileNo = Integer.parseInt(nextFileName.replaceAll(".rcd", ""));

			//連番ならばファイル名の番号の差異は「1」になる
			if(nextFileNo - currentFileNo != 1){
				System.out.println("売上ファイル名が連番になっていません");
				return null;
			}
		}

		BufferedReader br = null;
		try{
			//１つの売上ファイルから読み取ったデータを格納
			for(String fileName : earingsFileNames){
				Vector earingsFileLine = new Vector();
				br = new BufferedReader(new FileReader(path + File.separatorChar + fileName));

				String line;
				while((line =  br.readLine()) != null){
					//空行を除外する
					if(!line.equals("")){
						//データを格納
						earingsFileLine.add(line);
					}
				}
				//売上ファイルは3つのデータの仕様なので3つ以外は除外する
				if(earingsFileLine.size() != 3){
					System.out.println(fileName + "のフォーマットが不正です");
					return null;
				}
				earingsFileList.put(fileName,earingsFileLine);
			}
		}catch(IOException e){
			System.out.println("売上ファイルが存在しません");
			return null;
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return aggregateEarings(earingsFileList);
	}

	public Map aggregateEarings(Map<String, Vector> earingsFileList ){
		Map aggregateData = new HashMap();
		//売上ファイル名をsetとして取得しIteratorへ
		Iterator<String> fileNames = earingsFileList.keySet().iterator();

		while(fileNames.hasNext()){
			String fileName = fileNames.next();

			//earingsFileList内は売上ファイル名をキーとして売上ファイルのデータをvectorで保持している
			Vector<String> earingsFileDate = earingsFileList.get(fileName);

			//要素１の支店コードを取得
			String branchCode = earingsFileDate.get(0);
			//要素２の商品コードを取得
			String commondityCode = earingsFileDate.get(1);
			//要素３の売上額を取得
			String amount = earingsFileDate.get(2);

			if(! amount.matches("[0-9]*$") && amount.length() <= 10){
				System.out.println(fileName + "の売上額が不正です");
				return null;
			}
			//売上ファイル内の支店コードが支店定義ファイルに存在するかチェック
			if(! getBranchLineData().containsKey(branchCode)){
				System.out.println(fileName + "の支店コードが不正です");
				return null;
			}
			//売上ファイル内の商品コードが支店定義ファイルに存在するかチェック
			if(! getCommodityLineData().containsKey(commondityCode)){
				System.out.println(fileName + "の商品コードが不正です");
				return null;
			}
			//各集計ファイルごとにmapに分ける
			aggregateData = setAggregateData(branchCode,aggregateData,amount);
			if(aggregateData == null){
				return null;
			}
			aggregateData = setAggregateData(commondityCode,aggregateData,amount);
			if(aggregateData == null){
				return null;
			}
		}
		return aggregateData;
	}

	protected Map setAggregateData(String code,Map<String,BigDecimal> aggregateData,String amount){
		//合計金額を格納
		BigDecimal sumAmount;
		//計算用にBigDecimalにする
		BigDecimal tempAmount = new BigDecimal(amount);

		//各コード別に集計
		if(aggregateData.containsKey(code)){
			sumAmount = aggregateData.get(code);
			sumAmount = sumAmount.add(tempAmount);
			//桁数チェック
			if(sumAmount.precision() > 10){
				System.out.println("合計金額が10桁を超えました");
				return null;
			}
			aggregateData.put(code, sumAmount);
		}else{
			aggregateData.put(code, tempAmount);
		}
		return aggregateData;
	}
}
