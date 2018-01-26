from collections import Counter
#
# from core.models import Activity, LifestyleEntity
#
#
# def get_jac_similarity(model1, model2):
#     model1_dict = model1.__dict__
#     model2_dict = model2.__dict__
#
#     similarity_cntr = 0
#
#     similarity_cntr += get_dict_similarity(model1_dict, model2_dict)
#
#     return similarity_cntr
#
#
# def get_dict_similarity(dict1, dict2):
#     similarity_cntr = 0
#
#     for key, val in dict1.items():
#
#         if key in dict2:
#             if dict1[key]:
#
#                 if dict1[key] == dict2[key]:
#                     similarity_cntr += 1
#
#                 if isinstance(dict1[key], dict) and isinstance(dict2[key], dict):
#                     similarity_cntr += get_dict_similarity(dict1[key], dict2[key])
#
#                 if isinstance(dict1[key], list) and isinstance(dict2[key], list):
#                     similarity_cntr += get_list_similarity(dict1[key], dict2[key])
#
#     return similarity_cntr
#
#
# def get_list_similarity(list1, list2):
#
#     c = list((Counter(list1) & Counter(list2)).elements())
#     return len(c)
#
#
# def get_person_similarity(p0, p1):
#
#     sim = get_dict_similarity(p0.__dict__, p1.__dict__)
#
#     a0 = list(Activity.objects.filter(person_id=p0.id))
#     a1 = list(Activity.objects.filter(person_id=p1.id))
#
#     l0 = list(LifestyleEntity.objects.filter(person_id=p0.id))
#     l1 = list(LifestyleEntity.objects.filter(person_id=p1.id))
#
#     a0 = set([a.name for a in a0])
#     a1 = set([a.name for a in a1])
#     l0 = set([l.name for l in l0])
#     l1 = set([l.name for l in l1])
#
#     sim += get_list_similarity(l0, l1)
#     sim += get_list_similarity(a0, a1)
#
#     return sim
from helpers.face.detect import get_face_similarities
from helpers.fb.scraper import get_posts_for_uid, get_profile_pic
from helpers.text_analysis.extract import extract_keywords
from spikey.models import Superstar


def get_similarity_score(keywords1, keywords2):

    keywords1 = set(keywords1)
    keywords2 = set(keywords2)

    uni_len = len(keywords1.union(keywords2))

    if uni_len == 0:
        return 0

    int_len = len(keywords1.intersection(keywords2))

    return int_len / uni_len , keywords1.union(keywords2)


def get_photo_score():
    pass

def get_most_similar_star(f_auth):

    posts = get_posts_for_uid("me", access_token=f_auth)
    pic_url = get_profile_pic("me", access_token=f_auth)

    ### Face Sim

    face_ids = []
    face_ids_map = {}

    for star in Superstar.objects.all():

        face_id = star.face_id

        face_ids.append(face_id)
        face_ids_map[face_id] = star.id

    face_sims = get_face_similarities(pic_url, face_ids)

    face_sim_map = {}
    for fsimf in face_sims:

        face_id = fsimf["faceId"]
        conf = fsimf["confidence"]
        star_id = face_ids_map[face_id]
        face_sim_map[star_id] = conf

    ### Keywords + Face sim

    keywords = extract_keywords(" ".join(posts))

    max_sim = -1
    max_uni = []
    max_face = 0.

    for star in Superstar.objects.all():

        s_keywords = star.keywords.split(";")
        key_sim, uni_stuff = get_similarity_score(keywords, s_keywords)

        face_sim = face_sim_map.get(star.id, 0.)

        s_sim = key_sim + face_sim * 5

        if "Robben" in star.name:
            s_sim += 10

        if s_sim > max_sim:
            fav_player = star
            max_uni = uni_stuff
            max_face = face_sim



    return fav_player.name, fav_player.url, max_face, tuple(max_uni)
