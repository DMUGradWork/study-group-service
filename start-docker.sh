#!/bin/bash

echo "🚀 Study Group Service Docker 실행 스크립트"
echo "=========================================="

# 도커가 실행 중인지 확인
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker가 실행되지 않았습니다. Docker를 시작해주세요."
    exit 1
fi

echo "📦 Docker 이미지 빌드 중..."
docker-compose build

echo "🗄️ 데이터베이스 및 서비스 시작 중..."
docker-compose up -d mysql redis rabbitmq

echo "⏳ 데이터베이스 초기화 대기 중..."
sleep 30

echo "🎯 애플리케이션 시작 중..."
docker-compose up -d study-group-service

echo "✅ 모든 서비스가 시작되었습니다!"
echo ""
echo "📋 서비스 정보:"
echo "  - Study Group Service: http://localhost:8080"
echo "  - MySQL: localhost:3306"
echo "  - Redis: localhost:6379"
echo "  - RabbitMQ Management: http://localhost:15672 (admin/admin123)"
echo ""
echo "📊 로그 확인:"
echo "  docker-compose logs -f study-group-service"
echo ""
echo "🛑 서비스 중지:"
echo "  docker-compose down"
