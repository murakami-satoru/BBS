package jp.co.iccom.satoru_uematsu.earings;

public class ImportCommodityFile extends ImportFile{

	ImportCommodityFile(){
		//商品定義ファイル名を定数で指定
		super.trgFileName = "commodity.lst";
		super.fnc = "商品";
	}

	protected boolean checkCode(String code){
		//商品コードは8桁固定
		if(code.length() != 8){
			return true;
		}
		//商品コードは英数字のみ
		if(code.matches("[0-9a-zA-Z]*$") != true){
			return true;
		}
		return false;
	}
}
