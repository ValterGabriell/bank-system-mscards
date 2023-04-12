<h1 align="center">Microserviços de avaliação de criação de cartões - Sistema Bancário</h1>
<p align="center"><i>Repositório responsável receber a requisição de criar cartão da fila do RabbitMQ e processar ela.</i></p>

##  Sobre esse projeto
Este é um projeto que deve ser rodado após o Eureka Server estar rodando, para que se registre no discovery server.


## Indíce
<!--ts-->
   * [Como usar?](#como-usar)
   * [Endpoints](#endpoints)
   * [Creditos](#creditos)
<!--te-->
  
<h1>Como usar?</h1>
<h2>Prerequisites</h2>
<p>O Eureka Server deve estar rodando, acesse-o <a href="https://github.com/ValterGabriell/bank-system-eureka-server">aqui</a>.</br>
<p>Clone ou baixe o repositório e start ele através de sua IDE de preferência rodando o método main da classe principal na pasta raíz da aplicação, feito isso, basta começar a usar :). O ideal é startar todos os outros microserviços antes de testar a aplicação.</p>

1 -> <a href="https://github.com/ValterGabriell/bank-system-eureka-server">Eureka Server</a></br>
2 -> <a href="https://github.com/ValterGabriell/bank-system-mscards">Microserviço responsável por criar cartões para os usuários</a></br>
3 -> <a href="https://github.com/ValterGabriell/bank-system-mscreditappraiser">Microserviço responsável verificar o crédito que o usuário terá e solicitar a emissão de cartão</a></br>
4 -> <a href="https://github.com/ValterGabriell/bank-system-gateway">Gateway para fazer o loadbalancer dos microserviços</a></br>


  
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
    <td>cpf do cliente</td>
  </tr> 
  </table>
  
  <h3>Request esperada</h3></br>

```bash
{
	"buyValue":1000
}
```

<h3>Resposta esperada</h3></br>

```bash
{
	"message": compra efetuada no valor 1000" ,
	"newLimite": 1925.0,
	"headerLocation": null
}
```

<h1>GET</h1></br>


<h2>Recuperar os cartões do cliente</h2>
<table>
  <tr>
    <th>Request</th>
    <th>Response</th>
    <th>Query</th>
    <th>URL</th>
  </tr>
  <tr>
    <td>/</td>
    <td>Busca dados do cliente</td>
    <td>cpf cliente</td>
    <td>http://localhost:9090/card?cpf=cpfCliente</td>
  </tr>
</table>



<h3>Resposta esperada</h3></br>

```

{
	"data": {
		"idClientCard": 1,
		"cpf": "22324671912",
		"card": {
			"cardId": "708b7164-e7c2-4983-849b-3c5fb7bbef3f",
			"cardLimit": 2925.00,
			"cardSecurityNumber": "096",
			"cardNumber": "9949604766967",
			"expireDate": "2025-04-10"
		},
		"cardLimit": 2925.00,
		"currentLimit": 1925.00
	},
	"message": "Tudo certo!",
	"headerLocation": null
}

```

</br>

<h1>Créditos</h1>

---

<a href="https://www.linkedin.com/in/valter-gabriel">
  <img style="border-radius: 50%;" src="https://user-images.githubusercontent.com/63808405/171045850-84caf881-ee10-4782-9016-ea1682c4731d.jpeg" width="100px;" alt=""/>
  <br />
  <sub><b>Valter Gabriel</b></sub></a> <a href="https://www.linkedin.com/in/valter-gabriel" title="Linkedin">🚀</ a>
 
Made by Valter Gabriel 👋🏽 Get in touch!

[![Linkedin Badge](https://img.shields.io/badge/-Gabriel-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/valter-gabriel/ )](https://www.linkedin.com/in/valter-gabriel/)

