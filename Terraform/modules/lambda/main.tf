resource "aws_iam_role" "lambda_exec_role" {
  name = var.role_name

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Action = "sts:AssumeRole"
      Effect = "Allow"
      Principal = {
        Service = "lambda.amazonaws.com"
      }
    }]
  })
}

resource "aws_iam_role_policy_attachment" "lambda_basic_execution" {
  role       = aws_iam_role.lambda_exec_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole"
}

resource "aws_lambda_function" "this" {
  function_name = var.function_name
  runtime       = "java11"
  role          = aws_iam_role.lambda_exec_role.arn
  handler       = var.handler
  timeout       = 10
  memory_size   = 512
  filename      = var.filename
  source_code_hash = filebase64sha256(var.filename)
}

resource "aws_iam_role_policy" "lambda_funcao_dois_dynamodb_policy" {
  name = "lambda-funcao-dois-dynamodb-policy"
  role = aws_iam_role.lambda_exec_role.name

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Action = [
          "dynamodb:PutItem"
        ],
        Resource = "arn:aws:dynamodb:sa-east-1:292210043664:table/ListaDeMercado"
      }
    ]
  })
}





