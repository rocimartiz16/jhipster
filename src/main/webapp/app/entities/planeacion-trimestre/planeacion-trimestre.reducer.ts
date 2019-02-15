import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPlaneacionTrimestre, defaultValue } from 'app/shared/model/planeacion-trimestre.model';

export const ACTION_TYPES = {
  FETCH_PLANEACIONTRIMESTRE_LIST: 'planeacionTrimestre/FETCH_PLANEACIONTRIMESTRE_LIST',
  FETCH_PLANEACIONTRIMESTRE: 'planeacionTrimestre/FETCH_PLANEACIONTRIMESTRE',
  CREATE_PLANEACIONTRIMESTRE: 'planeacionTrimestre/CREATE_PLANEACIONTRIMESTRE',
  UPDATE_PLANEACIONTRIMESTRE: 'planeacionTrimestre/UPDATE_PLANEACIONTRIMESTRE',
  DELETE_PLANEACIONTRIMESTRE: 'planeacionTrimestre/DELETE_PLANEACIONTRIMESTRE',
  RESET: 'planeacionTrimestre/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPlaneacionTrimestre>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PlaneacionTrimestreState = Readonly<typeof initialState>;

// Reducer

export default (state: PlaneacionTrimestreState = initialState, action): PlaneacionTrimestreState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PLANEACIONTRIMESTRE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PLANEACIONTRIMESTRE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PLANEACIONTRIMESTRE):
    case REQUEST(ACTION_TYPES.UPDATE_PLANEACIONTRIMESTRE):
    case REQUEST(ACTION_TYPES.DELETE_PLANEACIONTRIMESTRE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PLANEACIONTRIMESTRE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PLANEACIONTRIMESTRE):
    case FAILURE(ACTION_TYPES.CREATE_PLANEACIONTRIMESTRE):
    case FAILURE(ACTION_TYPES.UPDATE_PLANEACIONTRIMESTRE):
    case FAILURE(ACTION_TYPES.DELETE_PLANEACIONTRIMESTRE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PLANEACIONTRIMESTRE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PLANEACIONTRIMESTRE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PLANEACIONTRIMESTRE):
    case SUCCESS(ACTION_TYPES.UPDATE_PLANEACIONTRIMESTRE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PLANEACIONTRIMESTRE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/planeacion-trimestres';

// Actions

export const getEntities: ICrudGetAllAction<IPlaneacionTrimestre> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PLANEACIONTRIMESTRE_LIST,
  payload: axios.get<IPlaneacionTrimestre>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPlaneacionTrimestre> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PLANEACIONTRIMESTRE,
    payload: axios.get<IPlaneacionTrimestre>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPlaneacionTrimestre> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PLANEACIONTRIMESTRE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPlaneacionTrimestre> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PLANEACIONTRIMESTRE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPlaneacionTrimestre> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PLANEACIONTRIMESTRE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
