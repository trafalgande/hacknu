import pandas as pd
import numpy as np
import pickle
import time
import sys


def current_milli_time():
    return round(time.time() * 1000)


names = {
    "imputer": f"models/imputer.pickle",
    "tpot_death": f"models/tpot_death.pickle",
    "tpot_sev": f"models/tpot_sev.pickle",
    "tpot_dead": f"models/tpot_dead.pickle",
    "tpot_alive": f"models/tpot_alive.pickle",
}
cols = [
    "sex",
    "ill_to_hosp",
    "age",
    "temp",
    "weakness",
    "smell_taste_loss",
    "chd",
    "aks",
    "cvd",
    "imv_days",
    "pcr_1",
    "leuk_1",
    "neut_1",
    "l_1",
    "ldh_1",
    "crp_1",
    "fer_1",
    "d_dym_1",
    "il6_1",
    "creat_1",
    "glu_1",
    "pct",
    "news_1",
    "resp_fail_5",
]
filename = sys.argv[1]

imputer = pickle.load(open(names["imputer"], "rb"))
tpot_death = pickle.load(open(names["tpot_death"], "rb"))
tpot_sev = pickle.load(open(names["tpot_sev"], "rb"))
tpot_dead = pickle.load(open(names["tpot_dead"], "rb"))
tpot_alive = pickle.load(open(names["tpot_alive"], "rb"))

data = pd.read_csv(filename, names=cols)
data = pd.DataFrame(imputer.transform(data), columns=data.columns, index=data.index)
death_rate = tpot_death.predict_proba(data)[0][1]
death = tpot_death.predict(data)[0]
sev_rate = max(tpot_sev.predict_proba(data)[0])
sev = tpot_sev.predict(data)[0]
days = np.nan
if death == 0:
    days = tpot_alive.predict(data)[0]
else:
    days = tpot_death.predict(data)[0]

answer = np.array([[death_rate, death, sev_rate, sev, days]])
np.savetxt(f"answer_{current_milli_time()}.csv", answer, delimiter=",", fmt='%f')
print(f'answer saved to answer_{current_milli_time()}.csv')
