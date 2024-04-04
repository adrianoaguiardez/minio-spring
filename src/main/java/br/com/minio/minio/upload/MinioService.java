package br.com.minio.minio.upload;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.minio.minio.util.RemoveAcentosUtil;
import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Item;

@Service
public class MinioService {

	@Autowired
	private MinioClient minioClient;

	@Value("${minio.bucket.name}")
	private String bucketName;

	public List<String> listarObjetos() {
		List<String> objects = new ArrayList<>();
		try {
			Iterable<Result<Item>> result = minioClient
					.listObjects(ListObjectsArgs.builder().bucket(bucketName).recursive(true).build());
			for (Result<Item> item : result) {
				objects.add(item.get().objectName());
			}
			return objects;
		} catch (Exception e) {

			throw new ObjectNotFoundException("Erro em listar objeto: " + e.getMessage());
		}

	}

	public InputStream buscarObjetoPeloNome(String nomeArquivo) {

		try {
			return minioClient
					.getObject(GetObjectArgs.builder().bucket(bucketName).object(nomeArquivo).build());
		} catch (Exception e) {

			throw new ObjectNotFoundException("Erro ao buscar objeto: ");
		}

	}

	public void remover(String nomeObjeto) {

		try {
			minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(nomeObjeto).build());

		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
				| IllegalArgumentException | IOException e) {

			throw new ObjectNotFoundException("Erro ao remover objeto: " + e.getMessage());
		}

	}

	public String uploadArquivo(MultipartFile arquivo) {
		String nomeArquivo = renomearArquivo(arquivo.getOriginalFilename());
		try {
			minioClient.putObject(PutObjectArgs.builder().bucket(bucketName)
					.object(nomeArquivo)
					.stream(arquivo.getInputStream(), arquivo.getSize(), -1).build());
			return nomeArquivo;
		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
				| IllegalArgumentException e) {

			throw new ObjectNotFoundException("Ocorreu um erro ao realizar upload: " + e.getMessage());

		} catch (IOException e) {

			throw new ObjectNotFoundException("Ocorreu um erro ao realizar upload: " + e.getMessage());
		}

	}

	private String renomearArquivo(String nomeOriginal) {

		if (nomeOriginal != null) {
			return UUID.randomUUID().toString() + "_" + RemoveAcentosUtil.removerAcentos(nomeOriginal.trim());
		}
		return null;
	}
}
