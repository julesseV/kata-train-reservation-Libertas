import httpx

def test_get_reservation():
    client = httpx.Client()
    ref1 = client.get("http://127.0.0.1:8082/booking_reference").text
    ref2 = client.get("http://127.0.0.1:8082/booking_reference").text
    print(ref1)
    print(ref2)
    assert ref1 != ref2


def test_get_data_for_train_no_such_train():
    client = httpx.Client()
    response = client.get("http://127.0.0.1:8081/data_for_train/no-such")
    assert response.status_code == 404


def test_get_data_for_existing_train():
    client = httpx.Client()
    response = client.get("http://127.0.0.1:8081/data_for_train/express_2000").json()
    assert 'seats' in response


def test_reset_train():
    client = httpx.Client()
    response = client.post("http://127.0.0.1:8081/reset/express_2000")
    assert response.status_code == 200


def test_reserve_ok():
    client = httpx.Client()
    response = client.post("http://127.0.0.1:8081/reset/express_2000")
    assert response.status_code == 200

    payload = { "train_id": "express_2000", "booking_reference": "abc123", "seats": ["1A", "2A"]}
    response = client.post("http://127.0.0.1:8081/reserve", json=payload)
    assert response.status_code == 200
    train = response.json()
    assert train["seats"]["1A"]["booking_reference"] == "abc123"

def test_reserve_conflict():
    client = httpx.Client()
    response = client.post("http://127.0.0.1:8081/reset/express_2000")
    assert response.status_code == 200
    # Book 2 seats
    payload = { "train_id": "express_2000", "booking_reference": "abc123", "seats": ["1A", "2A"]}
    response = client.post("http://127.0.0.1:8081/reserve", json=payload)
    # Book 2 more seats, creating a conflict
    payload = { "train_id": "express_2000", "booking_reference": "cde456", "seats": ["2A", "3A"]}
    response = client.post("http://127.0.0.1:8081/reserve", json=payload)

    assert response.status_code == 409
    assert "abc123" in response.text
    assert "cde456" in response.text