package br.com.minio.minio.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RemoveAcentosUtil {

	private static final String ACENTUADO = "çÇáéíóúýÁÉÍÓÚÝàèìòùÀÈÌÒÙãõñäëïöüÿÄËÏÖÜÃÕÑâêîôûÂÊÎÔÛ";
	private static final String SEMACENTO = "cCaeiouyAEIOUYaeiouAEIOUaonaeiouyAEIOUAONaeiouAEIOU";
	private static char[] tabela;

	static {
		tabela = new char[256];
		for (int i = 0; i < tabela.length; ++i) {
			tabela[i] = (char) i;
		}
		for (int i = 0; i < ACENTUADO.length(); ++i) {
			tabela[ACENTUADO.charAt(i)] = SEMACENTO.charAt(i);
		}
	}

	public static String removerAcentos(final String palavra) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < palavra.length(); ++i) {
			char ch = palavra.charAt(i);
			if (ch < 256) {
				sb.append(tabela[ch]);
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}
}
