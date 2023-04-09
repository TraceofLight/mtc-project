FROM python:3.9.13

WORKDIR /app
COPY . /app

RUN /usr/local/bin/python -m pip install --upgrade pip
RUN pip3 install --upgrade --no-deps --force-reinstall -r requirements.txt

CMD ["python", "./app.py"]
