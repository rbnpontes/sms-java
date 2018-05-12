# sms-java
# Uso Geral do Projeto
Como clonar um Repositorio ?
* Escolha uma pasta no Window e naveque até ela usando o CMD
* `Exemplo: cd C:\Users\Ruben\Documents\Projects\ProjectLeo\sms-java`
* Após isso clone o repositorio em na sua pasta
* `git clone https://github.com/rbnpontes/sms-java.git`
> Lembrando que uma vez feito este procedimento, não precisará ser feito novamente.
# Sincronizando repositorio com a Nuvem
* Antes de sincronizar, caso você tenha adicionada um novo arquivo, é necessario que adicione ao repositorio usando o comando:
`git add . `
* Para fazer qualquer sincronização com o repositorio remoto, é necessario criar um **commit** relatando todas as modificações feitas.
 Pra isso use o comando `git commit -m "Mensagem"`
 * Feito o commit, use o comando `git push` para sincronizar com o repositorio.
# Atualizando Codigo Modificado
 * Para atualizar o codigo modificado por outra pessoa, use o comando `git pull` , o git irá atualizar o seu codigo para a versão atual
 é interessante toda vez que for **iniciar** o projeto realizar este comando.


# Comandos rápidos
## Clonando Repositorio ##
  `cd C:\DIRETORIO`
  `git clone https://github.com/rbnpontes/sms-java.git`
## Adicionando novo arquivo ##
  `git add . `
## Sincronizando repositorio com a nuvem ##
  `git commit -m "Mensagem"`
  `git push`
## Atualizando Codigo ##
  `git pull` ou `git update`

>Use estes comandos um após o outro e não de uma vez
>Existe um codigo de exemplo no pacote `samples`

