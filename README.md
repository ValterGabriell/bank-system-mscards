<h1 align="center">Microserviços de avaliação de criação de cartões - Empresa de Software</h1>
<p align="center"><i>Repositório responsável receber a requisição de criar cartão da fila do RabbitMQ e processar ela.</i></p>

##  Sobre esse projeto
Este é um projeto que deve ser rodado após o Eureka Server estar rodando, para que se registre no discovery server.


## Indíce
<!--ts-->
   * [Como usar?](#como-usar)
   * [Endpoints](#endpoints)
   * [Testes](#testes)
   * [Creditos](#creditos)
<!--te-->
  
<h1>Como usar?</h1>
<p>O Eureka Server deve estar rodando, acesse-o <a href="https://github.com/ValterGabriell/bank-system-eureka-server">aqui</a>.</br>
<p>Clone ou baixe o repositório e start ele através de sua IDE de preferência rodando o método main da classe principal na pasta raíz da aplicação, feito isso, basta começar a usar :). O ideal é startar todos os outros microserviços antes de testar a aplicação.</p>
<p>Além disso, é fundamental ter um container do RabbitMQ no Docker rodando com usuario e senha padrao (guest, guest) para o microserviço poder receber a requisição da fila.</p>

1 -> <a href="https://github.com/ValterGabriell/bank-system-eureka-server">Eureka Server</a></br>
2 -> <a href="https://github.com/ValterGabriell/bank-system-mscards">Microserviço responsável por criar cartões para os usuários</a></br>
3 -> <a href="https://github.com/ValterGabriell/bank-system-mscreditappraiser">Microserviço responsável verificar o crédito que o usuário terá e solicitar a emissão de cartão</a></br>
4 -> <a href="https://github.com/ValterGabriell/software-company-mslead">Microserviço responsável pela criação dos líderes das squads</a></br>
5 -> <a href="https://github.com/ValterGabriell/software-company-mscolaborators">Microserviço responsável pela criação dos colaboradores das squads</a></br>
6 -> <a href="https://github.com/ValterGabriell/bank-system-gateway">Gateway para fazer o loadbalancer dos microserviços</a></br>


  
<h1>Endpoints</h1>
<h3>BASE URL</h3>

```bash
http://localhost:8080/card
``` 
<h1>POST</h1></br>

<h2>Realizar compra</h2>

<table>
  <tr>
    <th>Request</th>
    <th>Response</th>
    <th>Query</th>
  </tr>
  <tr>
    <td>/buy</td>
    <td>realizar compra</td>
    <td>id</td>
  </tr> 
  </table>
  
  <h3>Request esperada</h3></br>

```bash
{
	"productValue":924,
	"product":"telefone motorola 8",
	"numberOfInstallments":4
}
```

<h3>Resposta esperada</h3></br>

```bash
{
	"message": "produto aprovado",
	"product": "telefone motorola 8",
	"protocol": "09f25f46-55aa-43ae-a1b6-e2c48d2c1efd"
}
```

<h1>GET</h1></br>


<h2>Recuperar os cartões do cliente</h2>
<table>
  <tr>
    <th>Request</th>
    <th>Response</th>
    <th>Query</th>
  </tr>
  <tr>
    <td>/</td>
    <td>Busca dados do cliente</td>
    <td>id</td>
  </tr>
</table>



<h3>Resposta esperada</h3></br>

```
{
	"data": {
		"idClientCard": 1,
		"identifier": "63856573232",
		"card": {
			"cardId": "27ffc99c-ccce-41ad-b13b-06f85c0bdb8c",
			"cardLimit": 1400.00,
			"cardSecurityNumber": "819",
			"cardNumber": "8200179374080",
			"expireDate": "2025-05-03"
		},
		"cardLimit": 1400.00,
		"currentLimit": 476.00
	},
	"message": "Tudo certo!",
	"headerLocation": null
}

```

<h1>DELETE</h1></br>

<h2>Deletar conta de usuário</h2>
<table>
  <tr>
    <th>Request</th>
    <th>Response</th>
    <th>Query</th>
  </tr>
  <tr>
    <td>/</td>
    <td>Busca cliente no database e deleta conta de usuario com seus cartoes caso ela exista e não tenha pendências</td>
    <td>id</td>
  </tr>
</table>



<h3>Resposta esperada</h3></br>

```
204 - No Content

```

</br>


<h1>Testes</h1>

<h4>Setup</h4>

```
    private RequestCardDataMock requestCardData;
    private BuyRequest buyRequest;

    @BeforeEach
    void setup() {
        requestCardData = new RequestCardDataMock();
        requestCardData.setCpf("00021645400");
        buyRequest = new BuyRequest();
        buyRequest.setProductValue(BigDecimal.valueOf(3500));
    }

```

<h3>Verifica se o CPF tem 11 digitos</h3></br>

```
   @Test
    @DisplayName(" it should return true when cpf have 11 chars")
    public void itShouldReturnTrueWhenCpfHave11Chars(){
        int cpf = requestCardData.getCpf().length();
        Assertions.assertEquals(11, cpf);
    }
    
```

<h3>Verifica se o CPF contém apenas numeros</h3></br>


```
    @Test
    @DisplayName("cpf must contains only numbers and return false if is not")
    public void itShouldReturnTrueWhenCpfHaveOnlyNumbers() {
        String regex = "^[0-9]+$";
        String cpf = requestCardData.getCpf();
        boolean matches = cpf.matches(regex);
        Assertions.assertTrue(matches);
    }
    
```

<h3>Verifica se o numero do cartao tem 13 digitos</h3></br>


```
  @Test
    void itShouldReturnTrueIfCardNumberHas13Digits() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 13; i++) {
            var random = new Random().nextInt(10);
            var numberIntoString = String.valueOf(random);
            stringBuilder.append(numberIntoString);
        }
        var stringFinal = stringBuilder.toString();
        assertEquals(13, stringFinal.length());
    }
    
```

<h3>Verifica se o valor da compra é maior que zero</h3></br>


```
     @Test
    @DisplayName("product value should be bigger than zero")
    public void itShouldReturnTrueWhenProductValueBiggerThanZero() {
        BigDecimal productValue = buyRequest.getProductValue();
        int i = productValue.intValue();
        Assertions.assertTrue(i > 0);
    }
    
```










<h1>Créditos</h1>

---

<a href="https://www.linkedin.com/in/valter-gabriel">
  <img style="border-radius: 50%;" src="https://user-images.githubusercontent.com/63808405/171045850-84caf881-ee10-4782-9016-ea1682c4731d.jpeg" width="100px;" alt=""/>
  <br />
  <sub><b>Valter Gabriel</b></sub></a> <a href="https://www.linkedin.com/in/valter-gabriel" title="Linkedin">🚀</ a>
 
Made by Valter Gabriel 👋🏽 Get in touch!

[![Linkedin Badge](https://img.shields.io/badge/-Gabriel-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/valter-gabriel/ )](https://www.linkedin.com/in/valter-gabriel/)

