package br.com.gra.query.Utils;

public class Utils {
	public String firstToUpercase(String str) {
		str = this.fixSpaces(str);
		String[] arrayStr = str.split(" ");
		str = "";
		for(String name : arrayStr) {
			if(name.length() > 0)
				str += name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase()+" ";
		}

		return str.trim();
	}
	public String fixSpaces(String str) {
		str = str.trim().replaceAll("\\s{2,}", " ");
		return str;
	}

}
