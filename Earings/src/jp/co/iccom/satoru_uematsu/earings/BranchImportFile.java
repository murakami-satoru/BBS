package jp.co.iccom.satoru_uematsu.earings;

public class BranchImportFile extends ImportFile{

	BranchImportFile(){
		//支店定義ファイル名を定数で指定
		super.trgFileName = "branch.lst";
		super.fnc = "支店";
	}

	protected void ckCode(String code) throws MyException{
		//支店コードは３桁固定
		if(code.length() != 3){
			throw new MyException("支店定義ファイルのフォーマットが不正です");
		}

		//支店コードは数字固定
		try {
			Integer.parseInt(code);
		} catch (NumberFormatException nfex) {
			throw new MyException("支店定義ファイルのフォーマットが不正です");
		}
	}

	protected void ckName(String name) throws MyException{
		//支店名にカンマは入らない
		if(name.indexOf(",") != -1){
			throw new MyException("支店定義ファイルのフォーマットが不正です");
		//支店名に改行は入らない
		}else if(name.indexOf("\\r\\n") != -1){
			throw new MyException("支店定義ファイルのフォーマットが不正です");
		}

	}
}
