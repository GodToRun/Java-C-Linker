package jcl;

public class test {

	public static void main(String[] args) {
		Linker.Link("stdlib.h>\n#include <stdio.h");
		for (String s : Linker.Call("malloc(sizeof(int));"))
			System.out.println(s);
	}

}
