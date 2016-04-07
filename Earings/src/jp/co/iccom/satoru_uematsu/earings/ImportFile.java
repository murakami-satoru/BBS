package jp.co.iccom.satoru_uematsu.earings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class ImportFile {

	String trgFileName;
	String fnc;

	abstract protected boolean checkCode(String code);

	public Map fileImport(String path) throws IOException {
		List fileLineData = new ArrayList();

		BufferedReader br = null;
		try{
			//各定義ファイルを読み込む
			br = new BufferedReader(new FileReader(path + File.separatorChar + trgFileName));
			String line;
			while((line = br.readLine()) != null){
				//空行を除外する
				if(!line.equals("")){
					fileLineData.add(line);
				}
			}
		}catch(IOException e){
			System.out.println(fnc + "定義ファイルが存在しません");
			return null;
		}finally{
			br.close();
		}
		return dataImport(fileLineData);
	}

	public Map dataImport(List<String> fileLines){
		Map fileData = new HashMap();

		for(String line : fileLines){
			//カンマを起点としてコードを名前を分ける
			String[] lineList = line.split(",");

			//定義ファイルはコードと名称のみなので
			//データが2つ以外のときはエラーとする
			//また、名称にカンマ、改行が含まれている場合もこちらのエラーに掛かる
			if(lineList.length != 2){
				System.out.println(fnc + "定義ファイルのフォーマットが不正です");
				return null;
			}

			String code = lineList[0];
			String name = lineList[1];
			//定義ファイルのコード重複をチェック
			if(fileData.containsKey(code)){
				System.out.println(fnc + "定義ファイルのフォーマットが不正です");
				return null;
			}

			//コードチェック
			if(checkCode(code)){
				System.out.println(fnc + "定義ファイルのフォーマットが不正です");
				return null;
			}
			fileData.put(code, name);
		}
		return fileData;
	}
}
