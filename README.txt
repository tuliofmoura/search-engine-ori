API references:
http://docs.oracle.com/javase/6/docs/api/java/util/StringTokenizer.html
http://jsoup.org/apidocs/
https://code.google.com/p/google-gson/

----------------------------------------------------------------------

1- ESTRUTURA DE ARQUIVOS

	- bin/
		+ fontes compiladas
	
	- doc_collection/
		+ coleção de documentos
	
	- lib/
		+ contém as bibliotecas utilizadas na aplicação
	
	- praticas/
		+ arquivos com o enunciado das práticas propostas
	
	- src
		+ código fonte
	
	- structure
		+ contém os arquivos resultantes do pré-processamento. Estes arquivos são estruturados no formato JSON e são carregados em memória enquanto a aplicação é executada

----------------------------------------------------------------------

2- IMPORTANTE:

Antes de executar a aplicação, o conteúdo da classe "src/structure/Constants" deve ser atualizado com o caminho absoluto para os diretórios "/structure" e "/doc_collection"

O pré-processamento (criação dos índices) apenas é feito se eles não existirem no diretório "/structure". Para forçar o pré-processamento é necessário apagar os seguintes arquivos do diretório:
	- files_index_json.txt
	- inverted_index.txt
	- list_of_terms_json.txt
	- reverse_inverted_index.txt

----------------------------------------------------------------------

3- RESOLUÇÃO DAS PRÁTICAS

	- Prática 1 -> a resolução pode ser visualizada pelos arquivos:
		+ /structure/list_of_terms_json.txt
		+ /structure/grafico_frequencia.png
	
	- Prática 2 -> as estruturas requeridas estão nos arquivos:
		+ /structure/files_index_json.txt
		+ /structure/inverted_index.txt
	
	- Prática 3 -> a estrutura requerida está no arquivo:
		+ /structure/reverse_inverted_index.txt
		
----------------------------------------------------------------------

4- EXECUÇÃO DO PROGRAMA

Certifique-se de ter executado os procedimentos do tópico 2.
A execução do programa ocorre da seguinte forma:
	- solicita que o usuário entre com um TERMO para consulta
		+ um termo válido é UMA PALAVRA (ex.: casa; boa-noite) que pode conter UM CARACTERE CURINGA (ex.: *ados; casa*; ca*nto)
	- loop
		+ solicita que o usuário entre com um OPERADOR BOOLEANO
			# operadores válidos: && (AND), || (OR) e !! (NOT)
		+ solicita outro termo
A qualquer altura o usuário pode clicar em CANCELAR em vez de entrar com um termo ou operador. Isso fará com que o programa retorne o processamento final da consulta.
