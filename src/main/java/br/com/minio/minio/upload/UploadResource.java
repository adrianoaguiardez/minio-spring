package br.com.minio.minio.upload;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/uploads")
public class UploadResource {

	@Autowired
	private MinioService minioService;

	@GetMapping
	public ResponseEntity<List<String>> buscarTodos() {

		return ResponseEntity.ok().body(minioService.listarObjetos());

	}

	@GetMapping("/{nomeArquivo}")
	public ResponseEntity<byte[]> buscarArquivoNomeMinio(@PathVariable String nomeArquivo) {

		try {
			byte[] arquivo = minioService.buscarObjetoPeloNome(nomeArquivo).readAllBytes();

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
					.body(arquivo);
		} catch (IOException e) {

			throw new ObjectNotFoundException("Erro em listar objeto: " + e.getMessage());
		}

	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> upload(
			@RequestPart(value = "arquivo") MultipartFile arquivo) {

		return ResponseEntity.ok().body(minioService.uploadArquivo(arquivo));
	}

	@DeleteMapping("/{nomeArquivo}")
	public ResponseEntity<String> delete(@PathVariable String nomeArquivo) {

		minioService.remover(nomeArquivo);
		return ResponseEntity.noContent().build();
	}

}
