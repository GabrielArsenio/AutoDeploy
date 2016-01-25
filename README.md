# AutoDeploy
# Realiza clone/pull de uma aplicação Java WEB a partir do endereço de um repositório git e constroi o .war para jogar no servidor de aplição.
# 
# Requisitos:
# JDK (Recomendado 8 ou superior)
# Apache Tomcat (Recomendado 8 ou superior)
# Apache Ant (Recomendado 1.9.4 ou superior)
#
# Instalação:
# 1 - Baixar e instalar o recursos vistos acima.
#
# 2 - Configurar as variáveis de ambiente apontando o diretório de instalação dos recursos conforme exemplos abaixo.
#   IMPORTANTE: Não esquecer da variável PATH apontando para a pasta /bin de cada recurso
#
#   JAVA_HOME=/opt/jdk8
#   CATALINA_HOME=/opt/tomcat8
#   ANT_HOME=/opt/apache-ant-1.9.4
#   PATH=$PATH:$JAVA_HOME/bin:$CATALINA_HOME/bin:$ANT_HOME/bin
#   export JAVA_HOME CATALINA_HOME ANT_HOME PATH
#
# 3 - Criar um arquivo chamado token.txt no na pasta /home e colocar uma senha qualquer. Ex.: /home/token.txt
#
# 4 - Gerar .war com Apache Ant ou NetBeans e colocar na pasta /webapps do tomcat
#
# 5 - Executar!
