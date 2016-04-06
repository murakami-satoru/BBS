package jp.co.iccom.satoru_uematsu.earings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

abstract public class ImportFile {

	String trgFileName;
	String fnc;

	abstract protected String ckCode(String code);

	public Vector fileImport(String path) throws IOException {
		Vector vector = new Vector();

		BufferedReader br = null;
		try{
			//各定義ファイルを読み込む
			FileReader fr = new FileReader(path + File.separatorChar + trgFileName);
			br = new BufferedReader(fr);
			String s;;
			while((s = br.readLine() )!= null){
				vector.add(s);
			}

		}catch(IOException e1){
			System.out.println(fnc + "定義ファイルが存在しません。");
			return null;
		}finally{
			br.close();
		}
		return vector;
	}

	public HashMap dataImport(Vector vector){
		HashMap map = new HashMap();

		for(int index = 0; index < vector.size(); index++){
			//定義ファイルの行を取得
			String line = (String) vector.get(index);
			//カンマを起点としてコードを名前を分ける
			String[] lineList = line.split(",");

			//定義ファイルはコードと名称のみなので
			//データが2つ以外のときはエラーとする
			if(lineList.length != 2){
				System.out.println(fnc + "定義ファイルのフォーマットが不正です");
				return null;
			}

			String code = lineList[0];
			String name = lineList[1];

			//コードチェック
			String judeStr = ckCode(code);
			if(judeStr == null){
				System.out.println(fnc + "定義ファイルのフォーマットが不正です");
				return null;
			}

			map.put(code, name);
		}

		return map;
	}
}
