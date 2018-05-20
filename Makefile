.PHONY: install site docker-install docker-start docker-stop docker-restart

VERSION=$(shell mvn -q -Dexec.executable='echo' -Dexec.args='$${project.version}' --non-recursive org.codehaus.mojo:exec-maven-plugin:exec)

install:
	mvn clean install

site:
	mvn clean install \
		-pl :site com.mgreis.delivery:gitbook-maven-plugin:serve \
		-Pdocs

docker-install:
	mvn -DskipTests\
		-Pdocker \
		-P-code-quality \
		-P-license-check \
		clean install

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
