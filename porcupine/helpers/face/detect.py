import cognitive_face as CF

KEY = '881e3da51c4f4304b7fca6e243e3aed4'  # Replace with a valid Subscription Key here.
CF.Key.set(KEY)

BASE_URL = 'https://westcentralus.api.cognitive.microsoft.com/face/v1.0/'  # Replace with your regional Base URL
CF.BaseUrl.set(BASE_URL)

def get_face_id(path):

    result = CF.face.detect(path)

    return result[0]["faceId"]



def get_face_similarities(path, star_face_ids):

    try:

        me_face = get_face_id(path)
        result = CF.face.find_similars(face_id=me_face, face_ids=star_face_ids, mode="matchFace")

    except:
        result = []

    return result


