from locust import HttpUser, task, between

BASE_HEADERS = {
    "User-Agent": "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/145.0.0.0 Safari/537.36",
    "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8",
    "Accept-Language": "en-US,en;q=0.5",
    "Referer": "https://www.n11.com/",
}


class N11SearchUser(HttpUser):
    host = "https://www.n11.com"
    wait_time = between(1, 3)

    @task(3)
    def search_laptop(self):
        with self.client.get(
            "/arama?q=laptop",
            headers=BASE_HEADERS,
            catch_response=True,
            name="Search: laptop"
        ) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"Unexpected response: {response.status_code}")

    @task(2)
    def search_telefon(self):
        with self.client.get(
            "/arama?q=telefon",
            headers=BASE_HEADERS,
            catch_response=True,
            name="Search: telefon araması"
        ) as response:
            if response.status_code == 200 :
                response.success()
            else:
                response.failure(f"Unexpected response- {response.status_code}")

    @task(1)
    def search_empty(self):
        with self.client.get(
            "/arama?q=  ",
            headers=BASE_HEADERS,
            catch_response=True,
            name="Search: empty"
        ) as response:
            if response.status_code in [200, 302]:
                response.success()
            else:
                response.failure(f"Unexpected status: {response.status_code}")

    @task(1)
    def search_special_chars(self):
        with self.client.get(
            "/arama?q=@#$%.,()",
            headers=BASE_HEADERS,
            catch_response=True,
            name="Search: special charecters"
        ) as response:
            if response.ok:
                response.success()
            else:
                response.failure(f"Unexpected status: {response.status_code}")