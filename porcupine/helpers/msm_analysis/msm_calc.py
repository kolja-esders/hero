import time
import requests
import operator
import numpy as np

# Variables
_url_ana = 'https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/analyze'
_url_emo = 'https://westus.api.cognitive.microsoft.com/emotion/v1.0/recognize'
_key_ana = "a186112682254c72840689dff4dcf36f"  # Here you have to paste your primary key
_key_emo = "7fa4470351244302a8df86d0a227201b"  # Here you have to paste your primary key
_maxNumRetries = 10


def processRequest(json, data, headers, params, _url):
    """
    Helper function to process the request to Project Oxford

    Parameters:
    json: Used when processing images from its URL. See API Documentation
    data: Used when processing image read from disk. See API Documentation
    headers: Used to pass the key information and the data type request
    """

    retries = 0
    result = None

    while True:

        response = requests.request('post', _url, json=json, data=data, headers=headers, params=params)

        if response.status_code == 429:

            print("Message: %s" % (response.json()))

            if retries <= _maxNumRetries:
                time.sleep(1)
                retries += 1
                continue
            else:
                print('Error: failed after retrying!')
                break

        elif response.status_code == 200 or response.status_code == 201:

            if 'content-length' in response.headers and int(response.headers['content-length']) == 0:
                result = None
            elif 'content-type' in response.headers and isinstance(response.headers['content-type'], str):
                if 'application/json' in response.headers['content-type'].lower():
                    result = response.json() if response.content else None
                elif 'image' in response.headers['content-type'].lower():
                    result = response.content
        else:
            print("Error code: %d" % (response.status_code))
            print("Message: %s" % (response.json()))

        break

    return result


def get_dominant_foreground_color(path_to_file):

    # Computer Vision parameters
    params = {'visualFeatures': 'Color'}

    headers = dict()
    headers['Ocp-Apim-Subscription-Key'] = _key_ana
    headers['Content-Type'] = 'application/json'

    json = {'url': path_to_file}
    data = None

    # data = None
    result = processRequest(json, data, headers, params, _url=_url_ana)

    return result['color']['dominantColorForeground']


def process_happiness(path_to_file):

    params = {}

    headers = dict()
    headers['Ocp-Apim-Subscription-Key'] = _key_emo
    headers['Content-Type'] = 'application/json'

    json = {'url': path_to_file}
    data = None

    # data = None
    result = processRequest(json, data, headers, params, _url=_url_emo)

    if len(result) > 0:
        face_count = len(result)
        happiness_list = []
        for face in result:
            happiness_list.append(face['scores']['happiness'])
        return face_count, happiness_list
    else:
        return 0, [0]

    print("\n\n")

    print(result)


def get_mia_san_mia_photo_score(path_to_file):
    fc, hp = process_happiness(path_to_file)
    hp = np.mean(hp)

    color = get_dominant_foreground_color(path_to_file)
    color_score = 0
    if color == "Red":
        color_score = 0.25

    msm_score = hp / 2 + max(fc, 10) * 0.025 + color_score

    return msm_score


def get_dominant_foreground_color_local(path_to_file):
    with open(path_to_file, 'rb') as f:
        data = f.read()

        # Computer Vision parameters

    json = None

    # Computer Vision parameters
    params = {'visualFeatures': 'Color'}

    headers = dict()
    headers['Ocp-Apim-Subscription-Key'] = _key_ana
    headers['Content-Type'] = 'application/octet-stream'

    # URL direction to image
    # urlImage = 'https://fussball-fanshop-bilder.de/bilder/fc-bayern-poster-team-jupp-17-18-22085-2.jpg'
    # json = { 'url': urlImage }
    json = None

    # data = None
    result = processRequest(json, data, headers, params, _url=_url_ana)

    return result['color']['dominantColorForeground']


def process_happiness_local(path_to_file):
    with open(path_to_file, 'rb') as f:
        data = f.read()

    # Computer Vision parameters
    json = None
    params = {}

    headers = dict()
    headers['Ocp-Apim-Subscription-Key'] = _key_emo
    headers['Content-Type'] = 'application/octet-stream'

    # URL direction to image
    # urlImage = 'https://fussball-fanshop-bilder.de/bilder/fc-bayern-poster-team-jupp-17-18-22085-2.jpg'
    # json = { 'url': urlImage }
    json = None

    # data = None
    result = processRequest(json, data, headers, params, _url=_url_emo)

    if len(result) > 0:
        face_count = len(result)
        happiness_list = []
        for face in result:
            happiness_list.append(face['scores']['happiness'])
        return face_count, happiness_list
    else:
        return 0, [0]

    print("\n\n")

    print(result)


def get_mia_san_mia_photo_score_local(path_to_file):
    fc, hp = process_happiness_local(path_to_file)
    hp = np.mean(hp)

    color = get_dominant_foreground_color_local(path_to_file)
    color_score = 0
    if color == "Red":
        color_score = 0.25

    msm_score = hp / 2 + min(fc, 10) * 0.025 + color_score

    return int(msm_score * 100) , int(hp * 10), min(fc, 10), int(color_score * 10)
