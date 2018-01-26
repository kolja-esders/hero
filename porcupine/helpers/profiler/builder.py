import urllib.request
import numpy as np

#
# from server.core.helpers.image_cap.sample import get_img_description
# from server.core.helpers.image_det.detect import detect
# from server.core.helpers.matching.similarity import get_person_similarity
# from server.core.helpers.profiler.extract import TextClassifier
#
# from server.core.helpers.fb.scraper import get_posts_for_uid, get_images_for_uid, get_personal_infos_for_id, \
#     get_likes_for_uid, get_family_for_uid, get_locations_for_uid
#
# # from core.models import Person, DetectionReason, FamilyMember, Location, Activity, Injury, Device, LifestyleEntity
# # #from server.core.models import Person, DetectionReason, FamilyMember, Location, Activity, Injury, Device, \
# # #    LifestyleEntity
# # from core.models import Contract
#
# UPDATE_IDS = []
# INCOME_LOOKUP = dict(
#     Tutor=450
# )
# DEV_VAL_MAP = dict(
#     tv="300",
#     oven="200",
#     laptop="700"
# )
#
# TextCls = TextClassifier()
#
#
#
#
# def find_best_persons(person):
#     ps = Person.objects.all()
#
#     p_sim_lst = []
#
#     for p in ps:
#         sim = get_person_similarity(person, p)
#         p_sim_lst.append((sim, p))
#
#     p_sim_lst.sort(reverse=True, key= lambda x : x[0])
#
#     return p_sim_lst[:4]
#
#
# def find_best_contracts(person):
#     cs = Contract.objects.all()
#
#     p_sim_lst = []
#
#     for c in cs:
#         sim = 0
#         try:
#             sim = get_person_similarity(person, c.proto)
#         except:
#             pass
#         p_sim_lst.append((sim, c))
#
#     p_sim_lst.sort(reverse=True, key= lambda x : x[0])
#
#     return p_sim_lst[:4]
#
#
#
# def find_contract_updates(person):
#
#     c_lst = []
#
#     for up_id in UPDATE_IDS:
#         c_lst.append(Contract.objects.get(id=up_id))
#
#     return c_lst
#
#
#
#
#
# def create_person_for(user):
#
#     access_token = user.access_token
#
#     if user.person is None:
#
#         print("Person Created !")
#
#         person = create_profile(access_token)
#         user.person = person
#         user.save()
#
#         nbps = find_best_persons(person)
#         person.nb_p1 = nbps[0][1]
#         person.nb_p2 = nbps[1][1]
#         person.nb_p3 = nbps[2][1]
#         person.nb_p4 = nbps[3][1]
#         person.save()
#
#         cbps = find_best_contracts(person)
#         person.nb_con1 = cbps[0]
#         person.nb_con2 = cbps[1]
#         person.nb_con3 = cbps[2]
#         person.nb_con4 = cbps[3]
#         person.save()
#
#         cups = find_contract_updates(person)
#         for cup in cups:
#             person.up_con.add(cup)
#         person.save()
#
#
#
# def create_profile(access_token):
#
#     person = Person()
#     person.save()
#
#     x_dict = dict()
#     x_dict['activities'] = []
#     x_dict['injury'] = []
#     x_dict['devices'] = []
#     x_dict['lifestyle'] = []
#     x_dict['locations'] = []
#
#     posts = get_posts_for_uid("me", access_token=access_token)
#     images = get_images_for_uid("me", access_token=access_token)
#     infos = get_personal_infos_for_id("me", access_token=access_token)
#     likes = get_likes_for_uid("me", access_token=access_token)
#     family = get_family_for_uid("me", access_token=access_token)
#     locs = get_locations_for_uid("me", access_token=access_token)
#
#
#     name = infos.get("name")
#     person.given_name = name.split(" ")[1]
#     person.surname = name.split(" ")[0]
#
#     person.fb_access_token = access_token
#
#     person.home_town = infos.get("hometown", {}).get("name")
#     person.gender = infos.get("gender", "")
#
#     work = infos.get("work")
#     if work is not None:
#         person.occupation = work[-1].get("position", {}).get("name", "")
#         person.company = work[-1].get("employer", {}).get("name", "")
#
#         person.income = INCOME_LOOKUP.get(person.occupation, "0")
#
#     person.email_address = infos.get("email", "")
#     person.birthday = infos.get("birthday", "")
#
#     person.profile_picture = infos.get("picture", {}).get("data", {}).get("url", "")
#
#     for fam_member in family:
#         fm = FamilyMember()
#         fm.name = fam_member.get("name", "")
#         fm.age = fam_member.get("age", "")
#         fm.gender = fam_member.get("gender", "")
#         fm.relation = fam_member.get("relation", "")
#         fm.person = person
#         fm.save()
#
#     for loc in locs:
#         ll = Location()
#         ll.name = loc.get("name", "")
#         ll.person = person
#         ll.save()
#
#     for post in posts:
#         rets = TextCls.classify_text(post)
#
#         for key, vals in rets.items():
#             x_dict[key].append(dict(
#                 val=vals[0],
#                 reason=DetectionReason(type="Post", text=vals[0])
#             ))
#
#     for image in images:
#         img_text = image["name"]
#         img_url = image["img_url"]
#
#         file_name, headers = urllib.request.urlretrieve(img_url)
#         img_desc = get_img_description(file_name)
#         img_objs = detect(file_name)
#
#         print(img_objs)
#
#         rets = TextCls.classify_text(img_text)
#         for key, vals in rets.items():
#             x_dict[key].append(dict(
#                 val=vals[0],
#                 reason=DetectionReason(type="Image", text=img_text, href_to_image=img_url)
#             ))
#
#         rets = TextCls.classify_text(img_desc)
#         for key, vals in rets.items():
#             x_dict[key].append(dict(
#                 val=vals[0],
#                 reason=DetectionReason(type="Image", href_to_image=img_url, text=img_desc)
#             ))
#
#         rets = TextCls.classify_text(" ".join([i['type'] for i in img_objs]))
#         for key, vals in rets.items():
#             x_dict[key].append(dict(
#                 val=vals[0],
#                 reason=DetectionReason(type="fb-image", href_to_image=img_url)
#             ))
#
#     for like in likes:
#         rets = TextCls.classify_text(like)
#
#         for key, vals in rets.items():
#             x_dict[key].append(dict(
#                 val=vals[0],
#                 reason=DetectionReason(type="fb-like", text=vals[0])
#             ))
#
#
#     prev_acts = set()
#     for it in x_dict['activities']:
#         it_val = it['val']
#         reason = it['reason']
#         reason.save()
#         if it_val not in prev_acts:
#             act = Activity()
#             act.name = it_val
#             act.frequency = str(np.random.randint(0,10))
#             act.detection_reason = reason
#             act.person = person
#             act.save()
#             prev_acts.add(it_val)
#
#     for it in x_dict['injury']:
#         it_val = it['val']
#         reason = it['reason']
#         reason.save()
#         inj = Injury()
#         inj.name = it_val
#         inj.detection_reason = reason
#         inj.person = person
#         inj.save()
#
#     for it in x_dict['devices']:
#         it_val = it['val']
#         reason = it['reason']
#         reason.save()
#         dev = Device()
#         dev.type = it_val
#         dev.estimated_price = DEV_VAL_MAP.get(it_val, "100.0")
#         dev.detection_reason = reason
#         dev.person = person
#         dev.save()
#
#     for it in x_dict['lifestyle']:
#         it_val = it['val']
#         reason = it['reason']
#         reason.save()
#         lif = LifestyleEntity()
#         lif.name = it_val
#         lif.detection_reason = reason
#         lif.person = person
#         lif.save()
#
#     for it in x_dict['locations']:
#         it_val = it['val']
#         reason = it['reason']
#         reason.save()
#         loc = Location()
#         loc.name = it_val
#         loc.detection_reason = reason
#         loc.person = person
#         loc.save()
#
#     print(x_dict)
#
#     person.save()
#
#     return person

#create_profile("EAACEdEose0cBAIQBk11ynMuvN3SgrnXgXDaAj1QvZAxkzHMglW1UpoRaHr1xJZCb6o0xNZAsfRQJs5vA0bmkq9ZCTqF10yEmXW73GlnUZCqIXvbbFcZBmHLgQz9F98ZAusHgFa0xuxUVPmJXGeyT0GQimZCKDZCMqc84NM9x61Iev7HvudbSYx3TSdZAJWYg3ye3sVsO8nYnckSovnRSk3lRtPxbUVktugElwZD")
