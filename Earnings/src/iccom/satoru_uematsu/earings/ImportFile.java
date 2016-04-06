package iccom.satoru_uematsu.earings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

abstract public class ImportFile {

	String trgFileName;
	String fnc;


	public static void main(String[] args){}

	abstract protected void ckCode(String code) throws MyException;
	abstract protected void ckName(String name) throws MyException;

	public Vector fileImport(String pass) throws MyException {
		Vector vector = new Vector();

		try{
			//各定義ファイルを読み込む
			FileReader fr = new FileReader(pass + "\\" + trgFileName);
			BufferedReader br = new BufferedReader(fr);
			String s;;
			while((s = br.readLine() )!= null){
				vector.add(s);
			}

			br.close();
		}catch(IOException e1){
			throw new MyException(fnc + "定義ファイルが存在しません。");
		}
		return vector;
	}

	public HashMap dataImport(Vector vector) throws MyException{
		HashMap map = new HashMap();

		for(int index = 0; index < vector.size(); index++){
			//定義ファイルの行を取得
			String line = (String) vector.get(index);
			String code;
			String name;

			//カンマを起点としてコードを名前を分ける
			code = line.substring(0,line.indexOf(","));
			name = line.substring(line.indexOf(",")+1);

			//コードチェック
			ckCode(code);
			//名前チェック
			ckName(name);

			map.put(code, name);
		}

		return map;
	}
}
