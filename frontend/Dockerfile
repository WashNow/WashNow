# 1) Build stage
FROM node:20-alpine AS build

WORKDIR /app

# Copia package.json e lockfile e instala dependências
COPY frontend/package.json frontend/package-lock.json* ./
RUN npm ci

# Copia todo o código e gera o build estático
COPY frontend/ .
RUN npm run build

# 2) Runtime stage
FROM node:20-alpine

WORKDIR /app

# Instala apenas o vite preview para servir o build
RUN npm install -g vite

# Copia dist do estágio de build
COPY --from=build /app/dist ./dist

EXPOSE 80

# Serve os arquivos estáticos em 0.0.0.0:80
CMD ["vite", "preview", "--host", "0.0.0.0", "--port", "80"]
