package jp.co.iccom.satoru_uematsu.earings;

public class ImportBranchFile extends ImportFile{

	ImportBranchFile(){
		//支店定義ファイル名を定数で指定
		super.trgFileName = "branch.lst";
		super.fnc = "支店";
	}

	protected boolean checkCode(String code){
		//支店コードは3桁固定
		if(code.length() != 3){
			return true;
		}
		//支店コードは数字固定
		if(code.matches("[0-9]*$") != true){
			return true;
		}
		return false;
	}
}
