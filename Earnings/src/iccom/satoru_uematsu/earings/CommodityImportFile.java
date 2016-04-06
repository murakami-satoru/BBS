package iccom.satoru_uematsu.earings;

public class CommodityImportFile extends ImportFile{

	CommodityImportFile(){
		//商品定義ファイル名を定数で指定
		super.trgFileName = "commodity.lst";
		super.fnc = "商品";
	}

	protected void ckCode(String code) throws MyException{
		//商品コードは8桁固定
		if(code.length() != 8){
			throw new MyException("商品定義ファイルのフォーマットが不正です");
		}
		//商品コードは英数字のみ
		if(code.matches("[0-9a-zA-Z]*$") != true){
			throw new MyException("商品定義ファイルのフォーマットが不正です");
		}
	}

	protected void ckName(String name) throws MyException{
		//商品名にカンマは入らない
		if(name.indexOf(",") != -1){
			throw new MyException("商品定義ファイルのフォーマットが不正です");

		//商品名に改行は入らない
		}else if(name.indexOf("\r\n") != -1){
			throw new MyException("4商品定義ファイルのフォーマットが不正です");
		}
	}
}
