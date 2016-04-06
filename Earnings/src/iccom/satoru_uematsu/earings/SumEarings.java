package iccom.satoru_uematsu.earings;

import java.util.HashMap;

public class SumEarings {

	public static void main(String[] args) {

		//コマンドライン引数を取得
		String pass = args[0];

		try{
			//支店定義ファイルの取得
			System.out.println("支店定義ファイルを読み込みます");
			BranchImportFile bIF = new BranchImportFile();
			HashMap branchMap = bIF.dataImport(bIF.fileImport(pass));

			//商品定義ファイルの取得
//			System.out.println("商品定義ファイルを読み込みます");
			CommodityImportFile cIF = new CommodityImportFile();
			HashMap commodityMap = cIF.dataImport(cIF.fileImport(pass));

			//売上ファイルの取得
//			System.out.println("売上ファイルを読み込みます");
			EaringsImportFile eIF = new EaringsImportFile();
			HashMap earingsMap = eIF.sumEarings(eIF.fileImport(pass),branchMap,commodityMap);

			BranchSumOutputFile bOF = new BranchSumOutputFile();
			bOF.outPut(pass, branchMap, earingsMap);
			CommoditySumOutputFile cOF = new CommoditySumOutputFile();
			cOF.outPut(pass, commodityMap, earingsMap);

		}catch(MyException e){
			e.printStackTrace();
		}catch(Exception e){
			System.out.println("予期せぬエラーが発生しました");
			e.printStackTrace();
		}
	}
}
