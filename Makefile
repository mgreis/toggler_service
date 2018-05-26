.PHONY: install install-tests site docker-install docker-start docker-stop docker-restart

VERSION=$(shell mvn -q -Dexec.executable='echo' -Dexec.args='$${project.version}' --non-recursive org.codehaus.mojo:exec-maven-plugin:exec)

install:
	mvn clean install

install-tests:
	mvn clean install -DskipTests=false

docker-install:
	mvn -f delivery/ff4j-springboot/pom.xml \
		clean install dockerfile:build


docker-start: docker-stop
	mvn -Pdocker\
		-P-code-quality \
		-P-license-check \
		-f docker/docker-assemble/pom.xml \
		io.fabric8:docker-maven-plugin:start

docker-stop:
	mvn -Pdocker\
		-P-code-quality \
		-P-license-check \
		-f docker/docker-assemble/pom.xml \
		io.fabric8:docker-maven-plugin:stop

docker-restart: docker-stop docker-start
