module "funcao_um" {
  source         = "../../../modules/lambda" #primeiro erro que fiz, coloquei errado o caminho
  function_name  = "funcao-um"
  role_name      = "lambda-funcao-um-role"
  handler        = "com.example.FuncaoUmHandler::handleRequest"
  filename       = "C:/Projetos/meu-projeto/Lambda/funcao-um/target/funcao-um-1.0-SNAPSHOT.jar"

}
