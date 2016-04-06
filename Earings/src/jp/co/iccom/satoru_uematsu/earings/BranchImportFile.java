package jp.co.iccom.satoru_uematsu.earings;

public class BranchImportFile extends ImportFile{

	BranchImportFile(){
		//支店定義ファイル名を定数で指定
		super.trgFileName = "branch.lst";
		super.fnc = "支店";
	}

	protected String ckCode(String code){
		//支店コードは3桁固定
		if(code.length() != 3){
			return null;
		}
		//支店コードは数字固定
		if(code.matches("[0-9]*$") != true){
			return null;
		}
		return "";
	}
}
