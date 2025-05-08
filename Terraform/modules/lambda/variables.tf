variable "function_name" {}
variable "handler" {}
variable "role_name" {}
variable "filename" {}

variable "runtime" {
  type    = string
  default = "java11"
}

variable "dynamodb_table_arn" {
  type = string
}
