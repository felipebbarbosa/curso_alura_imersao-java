import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        // fazer uma conexão http e buscar os top 250 filmes
        String url = "https://api.mocki.io/v2/549a5d8b";
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(URI.create(url)).GET().build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam (titulo, poster, classificacao)
        var parser = new JsonParser();
        List<Map<String, String>> filmes = parser.parse(body);

        // exibir e manipular os dados
        var geradora = new GeradoraDeFigurinhas();
        for (Map<String,String> filme : filmes) {
           String urlImagem = filme.get("image");
           String titulo = filme.get("title");
           InputStream input = new URL(urlImagem).openStream();

           String nomeArquivo = titulo + ".png";

           geradora.cria(input, nomeArquivo);

           System.out.println(titulo);
           System.out.println();
        }

    }
}
