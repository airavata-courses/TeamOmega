FROM alpine:3.4

RUN apk add --no-cache python && \
    python -m ensurepip && \
    rm -r /usr/lib/python*/ensurepip && \
    pip install --upgrade pip setuptools && \
    rm -r /root/.cache \
  	&& rm -rf /var/cache/apk/*




COPY ./requirements.txt /app/requirements.txt

WORKDIR /app

RUN pip install -r /app/requirements.txt

COPY . /app

EXPOSE 5678

CMD ["python","rabbit.py"]
