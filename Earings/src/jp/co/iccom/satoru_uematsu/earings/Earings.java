package jp.co.iccom.satoru_uematsu.earings;

import java.util.Map;

public class Earings {

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
			ImportFile importBranchFile = new ImportBranchFile();
			Map brachLineData = importBranchFile.fileImport(path);
			checkNull(brachLineData);

			//商品定義ファイルの取得
			ImportFile importCommodityFile = new ImportCommodityFile();
			Map commodityLineData = importCommodityFile.fileImport(path);
			checkNull(commodityLineData);

			//売上ファイルの取得
			ImportEaringsFile importEaringsFile = new ImportEaringsFile();
			importEaringsFile.setBranchLineData(brachLineData);
			importEaringsFile.setCommodityLineData(commodityLineData);
			Map earingsMap = importEaringsFile.fileImport(path);
			checkNull(earingsMap);

			//支店別集計ファイルの出力
			OutputFile outputBranchFile = new OutputBranchFile();
			outputBranchFile.output(path, brachLineData, earingsMap);
			//商品別集計ファイルの出力
			OutputFile outputCommodityFile = new OutputCommodityFile();
			outputCommodityFile.output(path, commodityLineData, earingsMap);

		}catch(Exception e){
			System.out.println("予期せぬエラーが発生しました");
			e.printStackTrace();
		}
	}

	//nullチェックが重複処理になるためメソッド化
	//returnでは終了できないのでexitを使用
	protected static void checkNull(Object o){
		if(o == null){
			System.exit(0);
		}
	}
}
