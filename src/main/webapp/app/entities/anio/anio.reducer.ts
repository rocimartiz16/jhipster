import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAnio, defaultValue } from 'app/shared/model/anio.model';

export const ACTION_TYPES = {
  FETCH_ANIO_LIST: 'anio/FETCH_ANIO_LIST',
  FETCH_ANIO: 'anio/FETCH_ANIO',
  CREATE_ANIO: 'anio/CREATE_ANIO',
  UPDATE_ANIO: 'anio/UPDATE_ANIO',
  DELETE_ANIO: 'anio/DELETE_ANIO',
  RESET: 'anio/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAnio>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type AnioState = Readonly<typeof initialState>;

// Reducer

export default (state: AnioState = initialState, action): AnioState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ANIO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ANIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ANIO):
    case REQUEST(ACTION_TYPES.UPDATE_ANIO):
    case REQUEST(ACTION_TYPES.DELETE_ANIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ANIO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ANIO):
    case FAILURE(ACTION_TYPES.CREATE_ANIO):
    case FAILURE(ACTION_TYPES.UPDATE_ANIO):
    case FAILURE(ACTION_TYPES.DELETE_ANIO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ANIO_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ANIO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ANIO):
    case SUCCESS(ACTION_TYPES.UPDATE_ANIO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ANIO):
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

const apiUrl = 'api/anios';

// Actions

export const getEntities: ICrudGetAllAction<IAnio> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ANIO_LIST,
    payload: axios.get<IAnio>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IAnio> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ANIO,
    payload: axios.get<IAnio>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IAnio> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ANIO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAnio> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ANIO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAnio> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ANIO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
