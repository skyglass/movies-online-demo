terraform {
  backend "s3" {
    bucket = "skycomposer-movie"
    key    = "terraform/backend"
    region = "us-west-1"
  }
}
