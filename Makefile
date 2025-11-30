# Makefile - TrailRacer Platform
# Place this file in the root of your project

.PHONY: all build images up down logs logs-command logs-query ps restart clean

# Path to docker-compose file
COMPOSE_FILE = docker/docker-compose.yml

# Default target: build images + start everything
all: images up

# Compile all modules
build:
	./gradlew clean build -x test

# Build Jib images with correct names (matches your build.gradle)
images:
	@echo "Building Jib images..."
	./gradlew :command-service:jibDockerBuild
	./gradlew :query-service:jibDockerBuild

# Start all services (PostgreSQL, Kafka, MailDev, command-service, query-service)
up:
	@echo "Starting TrailRacer Platform..."
	docker compose -f $(COMPOSE_FILE) up -d

# Stop everything
down:
	docker compose -f $(COMPOSE_FILE) down

# Full restart (recommended after code changes)
restart: down images up

# Clean everything (volumes + networks + images)
clean: down
	docker compose -f $(COMPOSE_FILE) down -v
	docker rmi image/command-service:latest image/query-service:latest || true
	docker system prune -f

# View logs
logs:
	docker compose -f $(COMPOSE_FILE) logs -f

logs-command:
	docker compose -f $(COMPOSE_FILE) logs -f command-service

logs-query:
	docker compose -f $(COMPOSE_FILE) logs -f query-service

# Show running containers
ps:
	docker compose -f $(COMPOSE_FILE) ps

# Health check
health:
	@echo "=== TrailRacer Health Check ==="
	@curl -f http://localhost:8080/actuator/health >/dev/null 2>&1 && echo "command-service: UP" || echo "command-service: DOWN"
	@curl -f http://localhost:8081/actuator/health >/dev/null 2>&1 && echo "query-service: UP" || echo "query-service: DOWN"
	@echo "MailDev UI: http://localhost:1081"
