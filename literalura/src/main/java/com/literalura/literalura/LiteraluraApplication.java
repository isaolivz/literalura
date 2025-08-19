package com.literalura.literalura;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.literalura.model.Autor;
import com.literalura.literalura.model.DadosLivro;
import com.literalura.literalura.model.Livro;
import com.literalura.literalura.repository.AutorRepository;
import com.literalura.literalura.repository.LivroRepository;
import com.literalura.literalura.service.ConsumoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.literalura.literalura.model.DadosApi;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	private final String ENDERECO = "https://gutendex.com/books/?search=";
	private final Scanner scanner = new Scanner(System.in);
	private final ConsumoApi consumoApi = new ConsumoApi();
	private final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private LivroRepository livroRepository;

	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		int opcao = -1;
		while (opcao != 0) {
			exibirMenu();
			try {
				opcao = scanner.nextInt();
				scanner.nextLine();
				switch (opcao) {
					case 1:
						buscarLivroPorTitulo();
						break;
					case 2:
						listarLivrosRegistrados();
						break;
					case 3:
						listarAutoresVivosPorAno();
						break;
					case 4:
						listarLivrosPorIdioma();
						break;
					case 5:
						listarAutoresRegistrados();
						break;
					case 0:
						System.out.println("Saindo...");
						break;
					default:
						System.out.println("Opção inválida!");
				}
			} catch (Exception e) {
				System.out.println("Entrada inválida. Por favor, digite um número do menu.");
				scanner.nextLine();
				opcao = -1;
			}
		}
	}

	private void exibirMenu() {
		System.out.println("\n---------------- LITERATURA - Catálogo de Livros -----------------");
		System.out.println("1 - Buscar livro por título");
		System.out.println("2 - Listar livros registrados");
		System.out.println("3 - Listar autores vivos em um determinado ano");
		System.out.println("4 - Listar livros por idioma");
		System.out.println("5 - Listar autores registrados");
		System.out.print("0 - Sair\nEscolha uma opção: ");
	}

	private void buscarLivroPorTitulo() {
		System.out.print("\nDigite o título do livro: ");
		String titulo = scanner.nextLine();
		String json = consumoApi.obterDados(ENDERECO + titulo.replace(" ", "%20"));

		try {
			DadosApi dadosApi = mapper.readValue(json, DadosApi.class);
			if (dadosApi.results() != null && !dadosApi.results().isEmpty()) {
				Livro livro = new Livro(dadosApi.results().get(0));

				Optional<Livro> livroExistente = livroRepository.findByTitulo(livro.getTitulo());

				if (livroExistente.isPresent()) {
					System.out.println("\nLivro já registrado no banco de dados.");
					System.out.println(livroExistente.get());
				} else {
					livroRepository.save(livro);
					System.out.println("\nLivro salvo com sucesso!");
					System.out.println(livro);
				}
			} else {
				System.out.println("\nLivro não encontrado na API.");
			}
		} catch (JsonProcessingException e) {
			System.out.println("Erro ao processar JSON: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Ocorreu um erro inesperado. Tente novamente ou verifique o título.");
			e.printStackTrace();
		}
	}

	private void listarLivrosRegistrados() {
		List<Livro> livros = livroRepository.findAll();
		livros.forEach(System.out::println);
	}

	private void listarAutoresVivosPorAno() {
		System.out.print("Digite o ano para buscar autores vivos: ");
		int ano = scanner.nextInt();
		scanner.nextLine();
		List<Autor> autores = autorRepository.buscarAutoresVivosPorAno(ano);
		autores.forEach(System.out::println);
	}

	private void listarLivrosPorIdioma() {
		System.out.print("Digite o idioma (ex: en, pt, fr): ");
		String idioma = scanner.nextLine();
		List<Livro> livros = livroRepository.findByIdioma(idioma);
		livros.forEach(System.out::println);
	}

	private void listarAutoresRegistrados() {
		System.out.println("\n--- Autores Registrados ---");
		List<Autor> autores = autorRepository.findAll();
		autores.forEach(System.out::println);
	}
}