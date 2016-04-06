package jp.co.iccom.satoru_uematsu.earings;

import java.util.HashMap;
import java.util.Vector;

public class SumEarings {

	public static void main(String[] args) {

		if(args.length == 0){
			System.out.println("コマンドライン引数を入れてください");
			return;
		}else if(args.length > 1){
			System.out.println("コマンドライン引数は１つにしてください");
			return;
		}

		//コマンドライン引数を取得
		String path = args[0];

		try{
			//支店定義ファイルの取得
			BranchImportFile bIF = new BranchImportFile();
			Vector branchVec = bIF.fileImport(path);
			ckNull(branchVec);
			HashMap branchMap = bIF.dataImport(branchVec);
			ckNull(branchMap);

			//商品定義ファイルの取得
			CommodityImportFile cIF = new CommodityImportFile();
			Vector commodityVec = cIF.fileImport(path);
			ckNull(commodityVec);
			HashMap commodityMap = cIF.dataImport(commodityVec);
			ckNull(commodityMap);

			//売上ファイルの取得
			EaringsImportFile eIF = new EaringsImportFile();
			HashMap earingsMap = eIF.fileImport(path);
			ckNull(earingsMap);
			earingsMap = eIF.sumEarings(earingsMap,branchMap,commodityMap);
			ckNull(earingsMap);

			//支店別集計ファイルの出力
			BranchSumOutputFile bOF = new BranchSumOutputFile();
			bOF.outPut(path, branchMap, earingsMap);
			//商品別集計ファイルの出力
			CommoditySumOutputFile cOF = new CommoditySumOutputFile();
			cOF.outPut(path, commodityMap, earingsMap);

		}catch(Exception e){
			System.out.println("予期せぬエラーが発生しました");
			e.printStackTrace();
		}
	}

	//nullチェックが重複処理になるためメソッド化
	//returnでは終了できないのでexitを使用
	protected static void ckNull(Object o){
		if(o == null){
//			System.out.println("おわり");
			System.exit(0);
		}
	}
}
