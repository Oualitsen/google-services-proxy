db_up:
	docker-compose -f docker-compose-db.yaml up

GIT_COMMIT := $(shell git rev-parse --short HEAD)


build:
	mvn clean install -DskipTests

clean_compile_test:
	mvn clean compile test

jar:
	mvn package

build_docker_image:
	docker build -t publik:$(GIT_COMMIT) -f Dockerfile .

compose_dev:
	docker-compose -f docker-compose-dev.yaml up -d 

compose_dev_down:
	docker ps --filter "ancestor=publik" --format "{{.ID}}" | xargs docker stop